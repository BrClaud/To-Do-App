package com.BrClaudProject.myprojecttry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.BrClaudProject.myprojecttry.login.LoginActivity;
import com.BrClaudProject.myprojecttry.notes.NotesActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btNotesMain(View view) {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }





    public void btSingOutMain(View view) {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "выход из аккаунта", Toast.LENGTH_SHORT);
        finish();
    }
}