package com.rrp.duetlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class UserDashboardActivity extends AppCompatActivity {
    private String studentRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            studentRoll =  bundle.getString("roll");
        }

        getSupportActionBar().setTitle("Dashboard");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(UserDashboardActivity.this, StudentDashboardActivity.class);
            intent.putExtra("roll",studentRoll);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserDashboardActivity.this, StudentDashboardActivity.class);
        intent.putExtra("roll",studentRoll);
        startActivity(intent);
        finish();
    }
}