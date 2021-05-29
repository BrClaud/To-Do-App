package com.BrClaudProject.myprojecttry.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.BrClaudProject.myprojecttry.MainActivity;
import com.BrClaudProject.myprojecttry.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText etLoginEmail, etLoginPassword;
    Button btLogin, btReg;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        Toast toast;
        if(currentUser != null)
        {
            toast = Toast.makeText(getApplicationContext(), "пользователь авторизован", Toast.LENGTH_LONG);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            toast = Toast.makeText(getApplicationContext(), "пользователь не авторизован", Toast.LENGTH_LONG);
        }
        toast.show();

    }

    private void init()
    {
        etLoginEmail = findViewById(R.id.etLoginEmail);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        btLogin = findViewById(R.id.btLogin);
        btReg = findViewById(R.id.btReg);

        mAuth = FirebaseAuth.getInstance();
    }

    public void btLogin(View view) {
        String email = etLoginEmail.getText().toString(), pass = etLoginPassword.getText().toString();
        if(!email.isEmpty() && !pass.isEmpty()){
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(), "вход успешный", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else
                    {
                        Toast.makeText(getApplicationContext(), "при входе произошла ошибка, попробуйте снова", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else {
            Toast toast = Toast.makeText(LoginActivity.this, "заполните все поля", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void btReg(View view) {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}