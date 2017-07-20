package com.example.shanig.doctorapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    private TextView signUp,forget;
    private EditText signInEmail,signInPass;
    private Button signInBtn;
    private ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signUp = (TextView) findViewById(R.id.signUp);
        forget = (TextView) findViewById(R.id.forgetPassword);
        signInBtn = (Button) findViewById(R.id.signIn);
        progressBar = (ProgressBar) findViewById(R.id.progressSignIn);
        firebaseAuth = FirebaseAuth.getInstance();
        signInEmail = (EditText) findViewById(R.id.signInEmail);
        signInPass = (EditText) findViewById(R.id.signInpassword);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signInUser();
                }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,resetPassword.class));
            }
        });
    }

    private void signInUser(){

        String getEmail = signInEmail.getText().toString();
        String getPass = signInPass.getText().toString();

        if(TextUtils.isEmpty(getEmail)){

            Toast.makeText(SignIn.this,getResources().getString(R.string.email),Toast.LENGTH_LONG).show();
            return;
        }


        else if(TextUtils.isEmpty(getPass)){

            Toast.makeText(SignIn.this,getResources().getString(R.string.password),Toast.LENGTH_LONG).show();
            return;
        }

        else {

            progressBar.setVisibility(View.VISIBLE);
            firebaseAuth.signInWithEmailAndPassword(getEmail,getPass).addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful()){

                        startActivity(new Intent(SignIn.this,Tabs.class));
                    }
                    else{

                        Toast.makeText(SignIn.this,"Something Went Wrong Please try again.",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
