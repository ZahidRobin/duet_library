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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminProfile extends AppCompatActivity implements View.OnClickListener {
    private EditText nameId,phoneId,emailId,passwordId,confirmPasswordId;
    private Button profileUpdateButton;

    private String name,phone,email,password;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        reference = FirebaseDatabase.getInstance().getReference("tbl_admin");
        adminProfileDataRetrieve();

        nameId = (EditText) findViewById(R.id.adminProfileNameId);
        phoneId = (EditText) findViewById(R.id.adminProfilePhoneId);
        emailId = (EditText) findViewById(R.id.adminProfileEmailId);
        passwordId = (EditText) findViewById(R.id.adminProfilePasswordId);
        confirmPasswordId = (EditText) findViewById(R.id.adminProfileConfirmPasswordId);
        profileUpdateButton = (Button) findViewById(R.id.adminProfileUpdateButton);

        profileUpdateButton.setOnClickListener(this);
    }

    public void adminProfileDataRetrieve(){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                AdminProfileClass adminProfileClass = snapshot.child("admin").getValue(AdminProfileClass.class);
                nameId.setText(adminProfileClass.getName());
                phoneId.setText(adminProfileClass.getPhone());
                emailId.setText(adminProfileClass.getEmail());
                passwordId.setText(adminProfileClass.getPassword());
                confirmPasswordId.setText(adminProfileClass.getPassword());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(AdminProfile.this, AdminDashboardActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminProfile.this, AdminDashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.adminProfileUpdateButton){
            UpdateAdminProfile();
        }
    }

    public void UpdateAdminProfile(){
        String name = nameId.getText().toString().trim();
        String phone = phoneId.getText().toString().trim();
        String email = emailId.getText().toString().trim();
        String password = passwordId.getText().toString().trim();
        String confPassword = confirmPasswordId.getText().toString().trim();

        if(!password.equals(confPassword)){
            Toast.makeText(getApplicationContext(),"Password and Confirm Password does not Match!",Toast.LENGTH_SHORT).show();
            return;
        }else if(name.isEmpty() || phone.isEmpty() || password.isEmpty()){
            Toast.makeText(getApplicationContext(),"Field can not be Empty!",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            AdminProfileClass adminProfileClass = new AdminProfileClass(name,phone,email,password);
            reference.child("admin").setValue(adminProfileClass)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Admin Profile Updated Successfully",Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
}