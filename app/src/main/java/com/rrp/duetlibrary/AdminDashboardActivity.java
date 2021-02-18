package com.rrp.duetlibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminDashboardActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView dashboardCardview,profileCardview,addStudentsCardview,addBooksCardview,bookIssuesCardview,bookReturnsCardview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        getSupportActionBar().hide();

        dashboardCardview = (CardView) findViewById(R.id.adminDashboardId);
        profileCardview = (CardView) findViewById(R.id.adminProfileId);
        addStudentsCardview = (CardView) findViewById(R.id.adminAddStudentsId);
        addBooksCardview = (CardView) findViewById(R.id.adminAddBooksId);
        bookIssuesCardview = (CardView) findViewById(R.id.adminBookIssuesId);
        bookReturnsCardview = (CardView) findViewById(R.id.adminBookReturnId);

        dashboardCardview.setOnClickListener(this);
        profileCardview.setOnClickListener(this);
        addStudentsCardview.setOnClickListener(this);
        addBooksCardview.setOnClickListener(this);
        bookIssuesCardview.setOnClickListener(this);
        bookReturnsCardview.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminDashboardActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.adminDashboardId){

        }else if(v.getId() == R.id.adminProfileId){
            Intent intent = new Intent(AdminDashboardActivity.this, AdminProfile.class);
            startActivity(intent);
            finish();
        }else if(v.getId() == R.id.adminAddStudentsId){
            Intent intent = new Intent(AdminDashboardActivity.this, AdminAddStudent.class);
            startActivity(intent);
            finish();
        }else if(v.getId() == R.id.adminAddBooksId){
            Intent intent = new Intent(AdminDashboardActivity.this, AdminAddBooks.class);
            startActivity(intent);
            finish();
        }else if(v.getId() == R.id.adminBookIssuesId){
            Intent intent = new Intent(AdminDashboardActivity.this, AdminBookIssues.class);
            startActivity(intent);
            finish();
        }else if(v.getId() == R.id.adminBookReturnId){
            Intent intent = new Intent(AdminDashboardActivity.this, AdminBookReturn.class);
            startActivity(intent);
            finish();
        }
    }
}