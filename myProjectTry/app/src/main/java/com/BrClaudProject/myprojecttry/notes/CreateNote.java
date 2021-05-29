package com.BrClaudProject.myprojecttry.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.BrClaudProject.myprojecttry.Const;
import com.BrClaudProject.myprojecttry.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class CreateNote extends AppCompatActivity {

    EditText etCreateNoteHeader, etCreateNoteText;
    DatabaseReference db;
    FirebaseAuth dbAuth;
    ImageView ivImage;
    StorageReference storageRef;
    DatabaseReference notes;
    private String text = "", header = "";
    Uri uploadUri;
    boolean addedIm = false;
    boolean image = false;
    Note noteToAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        init();
    }

    private void init(){
        etCreateNoteHeader = findViewById(R.id.etCreateNoteHeader);
        etCreateNoteText = findViewById(R.id.etCreateNoteText);

        dbAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        ivImage = findViewById(R.id.ivImageCreateNote);
        storageRef = FirebaseStorage.getInstance().getReference().child(Const.USERS).child(dbAuth.getCurrentUser().getUid()).child(Const.IMAGES);
    }


    public void btCreateNote(View view) {
        text = etCreateNoteText.getText().toString();
        header = etCreateNoteHeader.getText().toString();

        notes = db.child(Const.USERS).child(dbAuth.getCurrentUser().getUid()).child(Const.NOTES);
        if(image)
        {
            uploadImage();
        }else
            saveWithoutImage();

    }

    private void getImage()
    {
        Intent intentImage = new Intent();
        intentImage.setType("image/*");
        intentImage.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentImage, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10 && data != null && data.getData() != null)
        {
            if(resultCode == RESULT_OK)
            {
                ivImage.setImageURI(data.getData());
                image = true;

            }
        }
    }


    private void uploadImage()
    {
        Bitmap btm = ((BitmapDrawable)ivImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        btm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        final StorageReference sRef = storageRef.child(System.currentTimeMillis() + "_Image");
        UploadTask uploadTask = sRef.putBytes(byteArray);
        Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return sRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                uploadUri = task.getResult();
                saveWithImage();

            }
        });

    }

    private void saveWithImage()
    {
        noteToAdd = new Note(text, header, uploadUri.toString());
        notes.push().setValue(noteToAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "заметка добавлена", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    private void saveWithoutImage(){
        noteToAdd = new Note(text, header);
        notes.push().setValue(noteToAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "заметка добавлена", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }



    public void btAddFileCreateNote(View view) {

    }


    public void btAddImageCreateNote(View view) {
        getImage();
    }
}