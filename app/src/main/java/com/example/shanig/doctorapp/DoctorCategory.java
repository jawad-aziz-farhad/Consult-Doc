package com.example.shanig.doctorapp;

/**
 * Created by ShaniG on 7/28/2017.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DoctorCategory extends navDrawer {

    private FragmentManager fragmentManager;
    private Fragment fragment = null;
    private ArrayList<Users> itemsArrayList;
    private ListView listView1;
    private ArrayList<String> usersKeys;
    private ProgressDialog progressDialog;
    private String fname,lname,address,city,country,category,image,gender;
    private String key;

    public DoctorCategory(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(DoctorCategory.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_doctor_category, null, false);
        drawer.addView(contentView, 0);

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new Fragment8();
        fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
        fragmentTransaction.commit();
    }
    }

