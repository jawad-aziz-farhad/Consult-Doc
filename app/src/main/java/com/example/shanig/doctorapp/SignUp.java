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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText dob;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    EditText email,password,fname,lname;
    Spinner type,gender;
    Button signUpBtn;
    String userId,userType,userGender,getEmail,getPass,getDob,getFname,getLname;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    DatabaseReference db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fname = (EditText) findViewById(R.id.firstName);
        lname = (EditText) findViewById(R.id.lastName);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        type = (Spinner) findViewById(R.id.user_type);
        gender = (Spinner) findViewById(R.id.gender);
        signUpBtn = (Button) findViewById(R.id.signUp);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("Users");
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

        getFname = fname.getText().toString().trim();
        getLname = lname.getText().toString().trim();
        getEmail = email.getText().toString().trim();
        getPass = password.getText().toString().trim();
        userType = type.getSelectedItem().toString();
        userGender = gender.getSelectedItem().toString();
        getDob = dob.getText().toString().trim();


        if(TextUtils.isEmpty(getFname)){
            Toast.makeText(SignUp.this,getResources().getString(R.string.firstName),Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(getLname)){
            Toast.makeText(SignUp.this,getResources().getString(R.string.lastName),Toast.LENGTH_LONG).show();
            return;
        }
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
                        userId = firebaseAuth.getCurrentUser().getUid();
                        Users user = new Users(getFname,getLname,userGender,userType,getDob);
                        db.child(userId).setValue(user);
                        firebaseAuth.signOut();
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

                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                        dob.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }

}
