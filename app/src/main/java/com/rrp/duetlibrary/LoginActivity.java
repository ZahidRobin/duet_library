package com.rrp.duetlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText loginId,passwordId;
    private Spinner spinner;
    private TextView textView,forgotPassword;
    private String[] users = {"Admin","Student"};
    private Button login;

    String loginst,passwordst;
    String userType;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        spinner = (Spinner) findViewById(R.id.loginTypeId);
        textView = (TextView) findViewById(R.id.loginSpinnerId);
        forgotPassword = (TextView) findViewById(R.id.forgotPasswordId);
        loginId = (EditText) findViewById(R.id.loginNumberId);
        passwordId = (EditText) findViewById(R.id.loginPasswordId);
        login = (Button) findViewById(R.id.loginId);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.login_spinner,R.id.loginSpinnerId,users);
        spinner.setAdapter(adapter);

        login.setOnClickListener(this);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Please Contact with Librarian",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        userType = spinner.getSelectedItem().toString();
        loginst = loginId.getText().toString().trim().toLowerCase();
        passwordst = passwordId.getText().toString().trim();
        if(loginst.isEmpty() || passwordst.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Input ID and Password",Toast.LENGTH_LONG).show();
        }else{
            if(userType == "Student"){
                reference = FirebaseDatabase.getInstance().getReference("tbl_students");
                reference.child(loginst)
                        .addListenerForSingleValueEvent(listener);
            }else if (userType == "Admin"){
                reference = FirebaseDatabase.getInstance().getReference("tbl_admin");
                reference.child(loginst)
                        .addListenerForSingleValueEvent(listener);
            }
        }
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists()){
                String databsePassword = snapshot.child("password").getValue(String.class);
                if(databsePassword.equals(passwordst)){
                    if(userType == "Student"){
                        Intent intent = new Intent(LoginActivity.this, StudentDashboardActivity.class);
                        intent.putExtra("roll",loginst);
                        startActivity(intent);
                        finish();
                    }else if(userType == "Admin"){
                        Intent intent = new Intent(LoginActivity.this, AdminDashboardActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong Username or Passwrod!",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "Wrong Username or Passwrod!",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(getApplicationContext(), error.toString(),Toast.LENGTH_LONG).show();
        }
    };


}