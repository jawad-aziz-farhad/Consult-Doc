package com.example.shanig.doctorapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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

import java.util.ArrayList;

/**
 * Created by ShaniG on 7/19/2017.
 */

public class Fragment4 extends Fragment {

    private ArrayList<articles> specializationArrayList;
    private ArrayList<String> certificateArrayList;
    private TextView services,serviceHeading;
    private ArrayList<String> articleKeys;
    private ProgressDialog progressDialog;

    public Fragment4() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment4, container, false);
        services = (TextView) fragmentLayout.findViewById(R.id.servicesDetails);
        serviceHeading = (TextView) fragmentLayout.findViewById(R.id.status1);

        populateData();
        return fragmentLayout;
    }

    private void populateData() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid().trim();

        mDatabase.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Users user = dataSnapshot.getValue(Users.class);
                serviceHeading.setText(getResources().getString(R.string.services)+"\n "+ user.getFirstName() + " " + user.getLastName());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR: ", "Failed to read value.", error.toException());
            }
        });


        /*for(int i=0;i<specializationArrayList.size();i++){
            if(i==0){

                services.append(specializationArrayList.get(i).getTitle());
            }
            else{
                services.append("\n"+specializationArrayList.get(i).getTitle());
            }

        }*/
    }
}
