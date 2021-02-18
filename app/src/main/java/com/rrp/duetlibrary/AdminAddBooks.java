package com.rrp.duetlibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class AdminAddBooks extends AppCompatActivity implements View.OnClickListener {
    private EditText ssbnId,bookNameId,authorNameId,languageId,editionId,numberOfCopyId;
    private ImageView showImageId;
    private Button coverPicId,addButtonId;
    private Uri uri;

    private static final int IMAGE_REQUEST = 1;

    private DatabaseReference reference;
    private StorageReference storageReference;
    private StorageTask storageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_books);

        getSupportActionBar().setTitle("Add Books");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        reference = FirebaseDatabase.getInstance().getReference("tbl_books");
        storageReference = FirebaseStorage.getInstance().getReference("tbl_books");

        ssbnId = (EditText) findViewById(R.id.addBooksSSBNId);
        bookNameId = (EditText) findViewById(R.id.addBooksBookNameId);
        authorNameId = (EditText) findViewById(R.id.addBooksAuthorNameId);
        languageId = (EditText) findViewById(R.id.addBooksLanguageId);
        editionId = (EditText) findViewById(R.id.addBooksEditionId);
        numberOfCopyId = (EditText) findViewById(R.id.addBooksNOCId);
        coverPicId = (Button) findViewById(R.id.addBooksCoverPicId);
        addButtonId = (Button) findViewById(R.id.addBooksButtonId);

        showImageId = (ImageView) findViewById(R.id.addBooksShowImageId);

        coverPicId.setOnClickListener(this);
        addButtonId.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Intent intent = new Intent(AdminAddBooks.this, AdminDashboardActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminAddBooks.this, AdminDashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addBooksCoverPicId:
                OpenImageChooser();
                break;

            case R.id.addBooksButtonId:
                if(storageTask != null && storageTask.isInProgress()){
                    Toast.makeText(getApplicationContext(),"Uploading in Progress!",Toast.LENGTH_LONG).show();
                }else{
                    SaveBooks();
                }
                break;
        }
    }

    private void OpenImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    public String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void SaveBooks(){
        String ssbn = ssbnId.getText().toString().trim();
        String bookName = bookNameId.getText().toString().trim();
        String authorName = authorNameId.getText().toString().trim();
        String language = languageId.getText().toString().trim();
        String edition = editionId.getText().toString().trim();
        String numberofCopy = numberOfCopyId.getText().toString().trim();

        if(ssbn.isEmpty() || bookName.isEmpty() || authorName.isEmpty() || numberofCopy.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please, Fill up all field!",Toast.LENGTH_SHORT).show();
            return;
        }
        StorageReference ref = storageReference.child(ssbn+"."+getFileExtension(uri));

        ref.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getApplicationContext(),"Book Uploaded Successfully.",Toast.LENGTH_SHORT).show();

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uriTask.isSuccessful());
                        Uri downloadUri = uriTask.getResult();

                        AddBookClass addBookClass = new AddBookClass(ssbn,bookName,authorName,language,edition,numberofCopy,downloadUri.toString());
                        reference.child(ssbn).setValue(addBookClass);
                        ssbnId.setText("");
                        bookNameId.setText("");
                        authorNameId.setText("");
                        languageId.setText("");
                        editionId.setText("");
                        numberOfCopyId.setText("");
                        showImageId.setImageResource(R.drawable.book_demo);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Book not Uploaded!",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode==RESULT_OK && data != null && data.getData()!= null){
            uri = data.getData();
            Picasso.get().load(uri).into(showImageId);
        }
    }
}