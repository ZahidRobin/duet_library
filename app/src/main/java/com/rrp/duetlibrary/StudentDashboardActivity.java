package com.rrp.duetlibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StudentDashboardActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView dashboardCardview,profileCardview,bookSearchCardview,notificationCardview;
    private String studentRoll;
//    private TextView ch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
           studentRoll =  bundle.getString("roll");
        }

        dashboardCardview = (CardView) findViewById(R.id.userDashboardId);
        profileCardview = (CardView) findViewById(R.id.userProfileId);
        bookSearchCardview = (CardView) findViewById(R.id.userBookSearchId);
        notificationCardview = (CardView) findViewById(R.id.userNotificationId);

//        ch = (TextView) findViewById(R.id.check);
//        ch.setText(studentRoll);



        dashboardCardview.setOnClickListener(this);
        profileCardview.setOnClickListener(this);
        bookSearchCardview.setOnClickListener(this);
        notificationCardview.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(StudentDashboardActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.userDashboardId){
            Intent intent = new Intent(StudentDashboardActivity.this, UserDashboardActivity.class);
            intent.putExtra("roll",studentRoll);
            startActivity(intent);
            finish();
        }else if(v.getId() == R.id.userProfileId){
            Intent intent = new Intent(StudentDashboardActivity.this, UserProfileActivity.class);
            intent.putExtra("roll",studentRoll);
            startActivity(intent);
            finish();
        }else if(v.getId() == R.id.userBookSearchId){
            Intent intent = new Intent(StudentDashboardActivity.this, UserBookSearchActivity.class);
            intent.putExtra("roll",studentRoll);
            startActivity(intent);
            finish();
        }else if(v.getId() == R.id.userNotificationId){
            Intent intent = new Intent(StudentDashboardActivity.this, UserNotificationActivity.class);
            intent.putExtra("roll",studentRoll);
            startActivity(intent);
            finish();
        }
    }
}