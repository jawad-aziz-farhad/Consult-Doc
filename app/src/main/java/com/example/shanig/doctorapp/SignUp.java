package com.example.shanig.doctorapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText dob;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    EditText email,password;
    Spinner type,gender;
    Button signUpBtn;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        type = (Spinner) findViewById(R.id.user_type);
        gender = (Spinner) findViewById(R.id.gender);
        signUpBtn = (Button) findViewById(R.id.signUp);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        firebaseAuth = FirebaseAuth.getInstance();
        dob = (EditText) findViewById(R.id.dob);

        dob.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {

                                       setDob();
                                   }
                               });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addUser();
            }
        });

    }

    private void addUser(){


        String getEmail = email.getText().toString().trim();
        String getPass = password.getText().toString().trim();

        if(TextUtils.isEmpty(getEmail)){
            Toast.makeText(SignUp.this,getResources().getString(R.string.email),Toast.LENGTH_LONG).show();
            return;
        }


        else if(TextUtils.isEmpty(getPass)){
            Toast.makeText(SignUp.this,getResources().getString(R.string.password),Toast.LENGTH_LONG).show();
            return;
        }

        else if(getPass.length()<6){
            Toast.makeText(SignUp.this,"Password is too short",Toast.LENGTH_LONG).show();
            return;
        }

        else {


            progressBar.setVisibility(View.VISIBLE);
            signUpBtn.setActivated(false);

            firebaseAuth.createUserWithEmailAndPassword(getEmail,getPass).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful()){
                        startActivity(new Intent(SignUp.this,SignIn.class));
                    }
                    else{
                        Toast.makeText(SignUp.this,"Authentication Failed" + task.getException(),Toast.LENGTH_LONG).show();

                        return;
                    }
                }
            });
        }

    }
    private void setDob() {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                SignUp.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year,month,day);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                dob.setText(date);
        }
        };

        }
}
