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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetPassword extends AppCompatActivity {

    EditText email;
    Button resetBtn;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        email = (EditText) findViewById(R.id.resetEmail);
        resetBtn = (Button) findViewById(R.id.resetPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressReset);
        firebaseAuth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getEmail = email.getText().toString();

                if(TextUtils.isEmpty(getEmail)){

                    Toast.makeText(resetPassword.this,getResources().getString(R.string.email),Toast.LENGTH_LONG).show();
                    return;
                }
                else{

                    progressBar.setVisibility(View.VISIBLE);
                    firebaseAuth.sendPasswordResetEmail(getEmail).addOnCompleteListener(resetPassword.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            progressBar.setVisibility(View.GONE);
                            if(task.isSuccessful()){

                                startActivity(new Intent(resetPassword.this,message.class));
                            }
                            else{

                                Toast.makeText(resetPassword.this,"Something Went Wrong Please Try Again",Toast.LENGTH_LONG).show();
                                return;
                            }
                        }
                    });
                }
            }
        });
    }
}
