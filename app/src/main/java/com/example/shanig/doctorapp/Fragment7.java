package com.example.shanig.doctorapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class Fragment7 extends Fragment {

    private Button addCertificate,done;
    private TextView certificateTitle,certifiedFrom,year;
    private String getTitle,getOrganization,getYear;
    private DatabaseReference databaseReference;
    private FragmentManager fragmentManager;
    private Fragment profileFragment,fragment;


    public Fragment7(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Certificates");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment7, container, false);

        certificateTitle = (TextView) fragmentLayout.findViewById(R.id.certificateTitle);
        certifiedFrom = (TextView) fragmentLayout.findViewById(R.id.certificateInstitute);
        year = (TextView) fragmentLayout.findViewById(R.id.certificateYear);
        addCertificate = (Button) fragmentLayout.findViewById(R.id.addCertificate);
        done = (Button) fragmentLayout.findViewById(R.id.donecertificate);

        addCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addCertificates();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager = getActivity().getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                profileFragment = new Profile();
                fragmentTransaction.replace(R.id.main_container_wrapper, profileFragment);
                fragmentTransaction.commit();
            }
        });

        return fragmentLayout;
    }

    private void addCertificates(){

        getTitle = certificateTitle.getText().toString().trim();
        getOrganization = certifiedFrom.getText().toString().trim();
        getYear = year.getText().toString().trim();

        if(TextUtils.isEmpty(getTitle)){

            Toast.makeText(getActivity(),getResources().getString(R.string.certificateTitle),Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(getOrganization)){

            Toast.makeText(getActivity(),getResources().getString(R.string.certifiedFrom),Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(getYear)){

            Toast.makeText(getActivity(),getResources().getString(R.string.certificationYear),Toast.LENGTH_LONG).show();
        }
        else{

            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            certificate addcerti = new certificate();
            addcerti.setCertificateTitle(getTitle);
            addcerti.setCertifiedFrom(getOrganization);
            addcerti.setCertificationYear(getYear);
            databaseReference.child(userId).push().setValue(addcerti);

            fragmentManager = getActivity().getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new Fragment7();
            fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
            fragmentTransaction.commit();

        }
    }
}
