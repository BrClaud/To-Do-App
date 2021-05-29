package com.BrClaudProject.myprojecttry.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.BrClaudProject.myprojecttry.Const;
import com.BrClaudProject.myprojecttry.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText etRegName, etRegEmail,etRegPass, etRegRetryPass;
    Spinner spGender;

    private String email, pass, name, retryPass;
    int gender;
    private FirebaseAuth mAuth;
    DatabaseReference db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init()
    {
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegName = findViewById(R.id.etRegName);
        etRegPass = findViewById(R.id.etRegPass);
        etRegRetryPass = findViewById(R.id.etRegRetryPass);
        spGender = findViewById(R.id.spRegGender);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();


    }

    private void updateProfileUser()
    {
        User newUser = new User(name, email, pass, gender);
       DatabaseReference users = db.child(Const.USERS).child(mAuth.getCurrentUser().getUid());
       users.child(Const.PROFILE).push().setValue(newUser);
    }



    public void btRegAcc(View view) {

        email = etRegEmail.getText().toString();
        pass = etRegPass.getText().toString();
        name = etRegName.getText().toString();
        retryPass = etRegRetryPass.getText().toString();
        gender = spGender.getSelectedItemPosition();

        if(!email.isEmpty() && !pass.isEmpty() && !name.isEmpty() && !retryPass.isEmpty() )
        {
            if(pass.equals(retryPass))
            {
                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(getApplicationContext(), "регистрация успешна.",
                                            Toast.LENGTH_SHORT).show();
                                    updateProfileUser();
                                    finish();

                                } else {
                                    Toast.makeText(getApplicationContext(), "произошла ошибка, попробуйте снова.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
            else
            {
                Toast.makeText(getApplicationContext(), "пароли не совпадают", Toast.LENGTH_SHORT).show();
            }



        }else
        {
            Toast.makeText(getApplicationContext(), "заполните все поля", Toast.LENGTH_LONG).show();
        }
    }
}