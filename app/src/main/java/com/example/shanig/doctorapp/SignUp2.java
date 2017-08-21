package com.example.shanig.doctorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignUp2 extends navDrawer {

    //private String id;
    private Button nextButton;
    private ProgressDialog progressDialog;
    private DatabaseReference db;
    private FirebaseAuth firebaseAuth;
    private TextView experience,contact,address,city,country;
    private Spinner category;
    private String getexperience,getcontact,getaddress,getcity,getcountry,gen,utype,date,uId,first,last,getCategory,imagePath;
    private double lati,longi;
    private List<String> popSpinner = new ArrayList<String>();
    private ArrayAdapter<String> dataAdapter;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(SignUp2.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_sign_up2, null, false);
        drawer.addView(contentView, 0);

        Intent intent = getIntent();
        String id = intent.getStringExtra("info");
        nextButton = (Button) findViewById(R.id.nextSignUp);
        experience = (TextView) findViewById(R.id.experience);
        contact = (TextView) findViewById(R.id.contactNumber);
        address = (TextView) findViewById(R.id.streetAddress);
        city = (TextView) findViewById(R.id.city);
        country = (TextView) findViewById(R.id.country);
        category = (Spinner) findViewById(R.id.category);
        db = FirebaseDatabase.getInstance().getReference().child("Users");

        dataAdapter = new ArrayAdapter<String>(SignUp2.this, android.R.layout.simple_spinner_item, popSpinner);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(dataAdapter);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Categories");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String data = (String) snapshot.child("title").getValue();
                    popSpinner.add(data);
                }
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*if (id!=null) {

            nextButton.setText(getResources().getString(R.string.editProfile));
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog = new ProgressDialog(SignUp2.this);
                    progressDialog.setMessage(getString(R.string._message));
                    progressDialog.show();
                    startActivity(new Intent(SignUp2.this,navigation.class));
                    progressDialog.cancel();
                }
            });
        }
        else{*/

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser(){

        getcontact = contact.getText().toString().trim();
        getaddress = address.getText().toString().trim();
        getexperience = experience.getText().toString().trim();
        getcity = city.getText().toString().trim();
        getcountry = country.getText().toString().trim();
        getCategory = category.getSelectedItem().toString();



        if(TextUtils.isEmpty(getcontact)){
            Toast.makeText(this, getResources().getString(R.string.contact), Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(getaddress)){
            Toast.makeText(this, getResources().getString(R.string.streetAddress), Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(getexperience)){
            Toast.makeText(this, getResources().getString(R.string.experience), Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(getcity)){
            Toast.makeText(this, getResources().getString(R.string.city), Toast.LENGTH_LONG).show();
            return;
        }
        else if(TextUtils.isEmpty(getcountry)){
            Toast.makeText(this, getResources().getString(R.string.country), Toast.LENGTH_LONG).show();
            return;
        }
        else{

            uId = firebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");

            database.child(uId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Users user = dataSnapshot.getValue(Users.class);
                    gen= user.getGender();
                    utype = user.getUserType();
                    date = user.getDob();
                    first = user.getFirstName();
                    last = user.getLastName();
                    lati = user.getLocationLati();
                    longi = user.getLocationLongi();
                    imagePath = user.getImagePath();

                    Users newUser = new Users(first,last,gen,utype,getexperience,getcontact,getaddress,getcity,getcountry,date,getCategory,lati,longi,imagePath);
                    database.child(uId).setValue(newUser);

                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Failure", "Failed to read value.", error.toException());
                }
            });

            startActivity(new Intent(SignUp2.this,qualifications.class));
        }

    }
}
