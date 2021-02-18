package com.rrp.duetlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText nameId,yearId,semesterId,sectionId,sessionId,phoneId,emailId,passwordId,confPasswordId;
    private TextView studentId,deptId;

    private Button updateButton;

    String studentRoll;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            studentRoll =  bundle.getString("roll");
        }

        reference = FirebaseDatabase.getInstance().getReference("tbl_students");

        studentProfileDataRetrieve();

        studentId  = (TextView) findViewById(R.id.userProfileStudentId);
        nameId = (EditText) findViewById(R.id.userProfileNameId);
        deptId = (TextView) findViewById(R.id.userProfileDeptId);
        yearId = (EditText) findViewById(R.id.userProfileYearId);
        semesterId = (EditText) findViewById(R.id.userProfileSemesterId);
        sectionId = (EditText) findViewById(R.id.userProfileSectionId);
        sessionId = (EditText) findViewById(R.id.userProfileSessionId);
        phoneId = (EditText) findViewById(R.id.userProfilePhoneNumberId);
        emailId = (EditText) findViewById(R.id.userProfileEmailId);
        passwordId = (EditText) findViewById(R.id.userProfilePasswordId);
        confPasswordId = (EditText) findViewById(R.id.userProfileConfPasswordId);
        updateButton = (Button) findViewById(R.id.userProfileUpdateButtonId);

        updateButton.setOnClickListener(this);

    }

    public void studentProfileDataRetrieve(){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentClass studentClass = snapshot.child(studentRoll).getValue(StudentClass.class);
                studentId.setText(studentClass.getRoll());
                nameId.setText(studentClass.getName());
                deptId.setText(studentClass.getDepartment());
                yearId.setText(studentClass.getYear());
                semesterId.setText(studentClass.getSemester());
                sectionId.setText(studentClass.getSection());
                sessionId.setText(studentClass.getSession());
                phoneId.setText(studentClass.getPhone());
                emailId.setText(studentClass.getEmail());
                passwordId.setText(studentClass.getPassword());
                confPasswordId.setText(studentClass.getPassword());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Something is Wrong here"+error,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(UserProfileActivity.this, StudentDashboardActivity.class);
            intent.putExtra("roll",studentRoll);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserProfileActivity.this, StudentDashboardActivity.class);
        intent.putExtra("roll",studentRoll);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.userProfileUpdateButtonId){
            UpdateStudentProfile();
        }
    }

    public void UpdateStudentProfile(){

        String name = nameId.getText().toString().trim();
        String year = yearId.getText().toString().trim();
        String dept = deptId.getText().toString().trim();
        String semester = semesterId.getText().toString().trim();
        String section = sectionId.getText().toString().trim().toUpperCase();
        String session = sessionId.getText().toString().trim();
        String phone = phoneId.getText().toString().trim();
        String email = emailId.getText().toString().trim();
        String password = passwordId.getText().toString().trim();
        String confPassword = confPasswordId.getText().toString().trim();

        if(!password.equals(confPassword)){
            Toast.makeText(getApplicationContext(),"Password and Confirm Password does not Match!",Toast.LENGTH_SHORT).show();
            return;
        }else if(name.isEmpty() || year.isEmpty() || semester.isEmpty() || section.isEmpty() || session.isEmpty() || phone.isEmpty()  || password.isEmpty()){
            Toast.makeText(getApplicationContext(),"Field can not be Empty!",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            StudentClass studentClass = new StudentClass(studentRoll,name,dept,year,semester,section,session,phone,email,password);
            reference.child(studentRoll).setValue(studentClass)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Student Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
}