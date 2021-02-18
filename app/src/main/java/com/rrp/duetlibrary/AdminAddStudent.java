package com.rrp.duetlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAddStudent extends AppCompatActivity {
    private EditText etRoll,etName,etDepartment,etYear,etSemester,etSession,etPassword;
    private String section,phone,email;

    private String roll,name,department,year,semester,session,password;

    private Button button;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_student);

        getSupportActionBar().setTitle("Add Students");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        reference = FirebaseDatabase.getInstance().getReference("tbl_students");

        etRoll = (EditText) findViewById(R.id.addStudentRollId);
        etName = (EditText) findViewById(R.id.addStudentNameId);
        etDepartment = (EditText) findViewById(R.id.addStudentDeptId);
        etYear = (EditText) findViewById(R.id.addStudentYearId);
        etSemester = (EditText) findViewById(R.id.addStudentSemesterId);
        etSession = (EditText) findViewById(R.id.addStudentSessionId);
        etPassword = (EditText) findViewById(R.id.addStudentPasswordId);

        button = (Button) findViewById(R.id.addStudentButtonId);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });
    }

    public void addStudent(){
        roll = etRoll.getText().toString().trim();
        name = etName.getText().toString().trim();
        department = etDepartment.getText().toString().toUpperCase().trim();
        year = etYear.getText().toString().trim();
        semester = etSemester.getText().toString().trim();
        session = etSession.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        section = "";
        phone="";
        email="";

        if(roll.isEmpty() || name.isEmpty() || department.isEmpty() || password.isEmpty()){
            Toast.makeText(AdminAddStudent.this,"Field can not be Empty!",Toast.LENGTH_SHORT).show();
        }else{
            StudentClass sc = new StudentClass(roll,name,department,year,semester,section,session,phone,email,password);
            reference.child(roll).setValue(sc)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminAddStudent.this,e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AdminAddStudent.this,"Students added Successfully",Toast.LENGTH_SHORT).show();
                            etRoll.setText("");
                            etName.setText("");
                            etDepartment.setText("");
                            etYear.setText("");
                            etSemester.setText("");
                            etSession.setText("");
                            etPassword.setText("");
                        }
                    });
        }
    }






    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(AdminAddStudent.this, AdminDashboardActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminAddStudent.this, AdminDashboardActivity.class);
        startActivity(intent);
        finish();
    }
}