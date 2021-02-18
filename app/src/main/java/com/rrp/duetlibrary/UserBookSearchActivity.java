package com.rrp.duetlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserBookSearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    AllBooksAdapter allBooksAdapter;
    private List<AddBookClass> addBookClassList;
    DatabaseReference reference;

    private String studentRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_book_search);

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            studentRoll =  bundle.getString("roll");
        }

        getSupportActionBar().setTitle("All Books");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.userAllbooksId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addBookClassList = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("tbl_books");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                    AddBookClass addBookClass = dataSnapshot1.getValue(AddBookClass.class);
                    addBookClassList.add(addBookClass);
                }
                allBooksAdapter = new AllBooksAdapter(UserBookSearchActivity.this,addBookClassList);
                recyclerView.setAdapter(allBooksAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error : "+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(UserBookSearchActivity.this, StudentDashboardActivity.class);
            intent.putExtra("roll",studentRoll);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserBookSearchActivity.this, StudentDashboardActivity.class);
        intent.putExtra("roll",studentRoll);
        startActivity(intent);
        finish();
    }
}