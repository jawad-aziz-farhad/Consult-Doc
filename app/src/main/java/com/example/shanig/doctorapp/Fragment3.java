package com.example.shanig.doctorapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by ShaniG on 7/19/2017.
 */

public class Fragment3 extends Fragment {

    private ArrayList<String> specializationTitle = new ArrayList<>();
    private ArrayList<String> certificateTitle = new ArrayList<>();
    private ArrayList<String> degreeTitle = new ArrayList<>();
    private ArrayList<String> specializationInstitute = new ArrayList<>();
    private ArrayList<String> certificateInstitute = new ArrayList<>();
    private ArrayList<String> degreeInstitute = new ArrayList<>();
    private TextView age,specialization,certificate,address,gender,degree,specialization1,certificate1,degree1,timings;
    private String userID;
    DatabaseReference specialDb,mDatabase,certificateDb,degreeDb;
    private ImageView location;
    private Button editInfo;
    private Button editSpecialization;
    private Button editCertification;
    private Button editAddress;
    private Button editDgree;
    private Button editTime;
    private ArrayList<String> articleKeys;
    private ProgressDialog progressDialog;
    private double latitude=30.3753,longitude=69.3451;
    private String add,selectedDoctor;
    private FragmentManager fragmentManager;
    private Fragment fragment = null;

    public Fragment3() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment3, container, false);

        age = (TextView) fragmentLayout.findViewById(R.id.age1);
        gender = (TextView) fragmentLayout.findViewById(R.id.gender1);
        specialization = (TextView) fragmentLayout.findViewById(R.id.special1);
        certificate = (TextView) fragmentLayout.findViewById(R.id.certificate1);
        degree = (TextView) fragmentLayout.findViewById(R.id.degree1);
        specialization1 = (TextView) fragmentLayout.findViewById(R.id.special2);
        certificate1 = (TextView) fragmentLayout.findViewById(R.id.certificate2);
        degree1 = (TextView) fragmentLayout.findViewById(R.id.degree2);
        address = (TextView) fragmentLayout.findViewById(R.id.completeAddress);
        location = (ImageView) fragmentLayout.findViewById(R.id.addressLocation);
        timings = (TextView) fragmentLayout.findViewById(R.id.allTimes);

        editInfo = (Button) fragmentLayout.findViewById(R.id.editInfo);
        editTime = (Button) fragmentLayout.findViewById(R.id.editTime);
        editSpecialization = (Button) fragmentLayout.findViewById(R.id.editspecialization);
        editCertification = (Button) fragmentLayout.findViewById(R.id.editCertification);
        editAddress = (Button) fragmentLayout.findViewById(R.id.editAddress);
        editDgree = (Button) fragmentLayout.findViewById(R.id.editDegrees);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        specialDb = FirebaseDatabase.getInstance().getReference().child("Specializations");
        certificateDb = FirebaseDatabase.getInstance().getReference().child("Certificates");
        degreeDb = FirebaseDatabase.getInstance().getReference().child("Degrees");

        selectedDoctor = getArguments().getString("option");
        userID = getArguments().getString("userKey");

        Toast.makeText(getActivity(), "OPTION IS: " + selectedDoctor + "\n ID IS: " + userID, Toast.LENGTH_SHORT).show();

        if(selectedDoctor.equals("selected")){

            selected();
        }
        else{
            userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            editProfile();
            ownProfile();
        }

        return fragmentLayout;
    }

    private void selected(){


        editTime.setVisibility(View.GONE);
        editDgree.setVisibility(View.GONE);
        editAddress.setVisibility(View.GONE);
        editCertification.setVisibility(View.GONE);
        editInfo.setVisibility(View.GONE);
        editSpecialization.setVisibility(View.GONE);
        mDatabase.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Users user = dataSnapshot.getValue(Users.class);

                age.setText(user.getDob());
                gender.setText(user.getGender());
                if(user.getAddress()!=null) {
                    address.setText(user.getAddress() + "," + user.getCity() + "," + user.getCountry());
                    add = user.getAddress()+user.getCity()+user.getCountry();
                    location.setImageResource(R.drawable.directions);
                    location.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //getLatLongFromAddress(add);

                            Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?f=d&daddr="+latitude+","+longitude);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                startActivity(mapIntent);
                            }
                            //startActivity(new Intent(getActivity(),MapLocation.class));
                        }
                    });
                }

                else{

                    address.setText(getResources().getString(R.string.noRecord));
                    location.setImageResource(R.drawable.directions);
                    location.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getLatLongFromAddress(add);
                            Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?f=d&daddr="+latitude+","+longitude);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                startActivity(mapIntent);
                            }
                            //startActivity(new Intent(getActivity(),MapLocation.class));
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR: ", "Failed to read value.", error.toException());
            }
        });

        timings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WeeklyTimings week = new WeeklyTimings(getActivity());
                week.getWindow().setBackgroundDrawableResource(R.drawable.edit_text_layout);
                week.show();
            }
        });
        specialDb.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {

                    for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                        String title = childSnap.child("specializationTitle").getValue().toString();
                        String institute = childSnap.child("specializationInstitute").getValue().toString();
                        specializationTitle.add(title);
                        specializationInstitute.add(institute);
                    }
                    populateSpecialization();
                }
                else{
                    specializationTitle.add(getResources().getString(R.string.noRecord));
                    specializationInstitute.add(getResources().getString(R.string.noRecord));
                    populateSpecialization();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("ERROR: ", "Failed to read value.", error.toException());
            }
        });
        degreeDb.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {

                    for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                        String title = childSnap.child("degreeTitle").getValue().toString();
                        String institute = childSnap.child("degreeInstitute").getValue().toString();
                        degreeTitle.add(title);
                        degreeInstitute.add(institute);
                    }
                    populateDegree();
                }
                else{

                    degreeTitle.add(getResources().getString(R.string.noRecord));
                    degreeInstitute.add(getResources().getString(R.string.noRecord));
                    populateDegree();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("ERROR: ", "Failed to read value.", error.toException());
            }
        });
        certificateDb.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChildren()) {

                    for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                        String title = childSnap.child("certificateTitle").getValue().toString();
                        String institute = childSnap.child("certifiedFrom").getValue().toString();
                        certificateTitle.add(title);
                        certificateInstitute.add(institute);
                    }
                    populateCertificate();
                }
                else{

                    certificateTitle.add(getResources().getString(R.string.noRecord));
                    certificateInstitute.add(getResources().getString(R.string.noRecord));
                    populateCertificate();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("ERROR: ", "Failed to read value.", error.toException());
            }
        });

    }
    private void ownProfile(){

        mDatabase.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Users user = dataSnapshot.getValue(Users.class);

                age.setText(user.getDob());
                gender.setText(user.getGender());
                if(user.getAddress()!=null) {
                    address.setText(user.getAddress() + "," + user.getCity() + "," + user.getCountry());
                    add = user.getAddress()+user.getCity()+user.getCountry();
                    location.setImageResource(R.drawable.directions);
                    location.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getLatLongFromAddress(add);

                            Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?f=d&daddr="+latitude+","+longitude);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                startActivity(mapIntent);
                            }
                            //startActivity(new Intent(getActivity(),MapLocation.class));
                        }
                    });
                }

                else{

                    address.setText(getResources().getString(R.string.noRecord));
                    location.setImageResource(R.drawable.directions);
                    location.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getLatLongFromAddress(add);
                            Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?f=d&daddr="+latitude+","+longitude);
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                startActivity(mapIntent);
                            }
                            //startActivity(new Intent(getActivity(),MapLocation.class));
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR: ", "Failed to read value.", error.toException());
            }
        });

        timings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WeeklyTimings week = new WeeklyTimings(getActivity());
                week.getWindow().setBackgroundDrawableResource(R.drawable.edit_text_layout);
                week.show();
            }
        });
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Timing.class));
            }
        });
        specialDb.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {

                    for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                        String title = childSnap.child("specializationTitle").getValue().toString();
                        String institute = childSnap.child("specializationInstitute").getValue().toString();
                        specializationTitle.add(title);
                        specializationInstitute.add(institute);
                    }
                    populateSpecialization();
                }
                else{
                    specializationTitle.add(getResources().getString(R.string.noRecord));
                    specializationInstitute.add(getResources().getString(R.string.noRecord));
                    populateSpecialization();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("ERROR: ", "Failed to read value.", error.toException());
            }
        });
        degreeDb.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {

                    for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                        String title = childSnap.child("degreeTitle").getValue().toString();
                        String institute = childSnap.child("degreeInstitute").getValue().toString();
                        degreeTitle.add(title);
                        degreeInstitute.add(institute);
                    }
                    populateDegree();
                }
                else{

                    degreeTitle.add(getResources().getString(R.string.noRecord));
                    degreeInstitute.add(getResources().getString(R.string.noRecord));
                    populateDegree();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("ERROR: ", "Failed to read value.", error.toException());
            }
        });
        certificateDb.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChildren()) {

                    for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                        String title = childSnap.child("certificateTitle").getValue().toString();
                        String institute = childSnap.child("certifiedFrom").getValue().toString();
                        certificateTitle.add(title);
                        certificateInstitute.add(institute);
                    }
                    populateCertificate();
                }
                else{

                    certificateTitle.add(getResources().getString(R.string.noRecord));
                    certificateInstitute.add(getResources().getString(R.string.noRecord));
                    populateCertificate();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("ERROR: ", "Failed to read value.", error.toException());
            }
        });
    }

    private void editProfile(){

        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = "editinfo";
                Intent intent = new Intent(getActivity(),SignUp2.class);
                intent.putExtra("info",id.toString());
                startActivity(intent);
            }
        });

        editSpecialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fragmentManager = getActivity().getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragment = new Fragment6();
                fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
                fragmentTransaction.commit();
            }
        });

        editCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fragmentManager = getActivity().getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragment = new Fragment7();
                fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
                fragmentTransaction.commit();
            }
        });

        editDgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fragmentManager = getActivity().getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragment = new Fragment5();
                fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
                fragmentTransaction.commit();
            }
        });

        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = "editinfo";
                Intent intent = new Intent(getActivity(),SignUp2.class);
                intent.putExtra("info",id.toString());
                startActivity(intent);
            }
        });

    }
    private void populateSpecialization() {

        for(int i=0;i<specializationTitle.size();i++){
            if(i==0){

                specialization.append(specializationTitle.get(i));
                specialization1.append(specializationInstitute.get(i));
            }
            else{
                specialization.append("\n\n"+ specializationTitle.get(i));
                specialization1.append("\n\n"+ specializationInstitute.get(i));
            }

        }
    }

    private void populateDegree() {

        for(int i=0;i<degreeTitle.size();i++){
            if(i==0){

                degree.append(degreeTitle.get(i));
                degree1.append(degreeInstitute.get(i));
            }
            else{
                degree.append("\n\n"+ degreeTitle.get(i));
                degree1.append("\n\n"+ degreeInstitute.get(i));
            }

        }
    }

    private void populateCertificate() {

        for(int i=0;i<certificateTitle.size();i++){
            if(i==0){

                certificate.append(certificateTitle.get(i));
                certificate1.append(certificateInstitute.get(i));
            }
            else{
                certificate.append("\n\n"+ certificateTitle.get(i));
                certificate1.append("\n\n"+ certificateInstitute.get(i));
            }

        }
    }

    private void getLatLongFromAddress(String address) {

        Geocoder coder = new Geocoder(getActivity());
        try {
            ArrayList<Address> adresses = (ArrayList<Address>) coder.getFromLocationName(address, 50);
            for(Address add : adresses){

                    longitude = add.getLongitude();
                    latitude = add.getLatitude();
               }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
