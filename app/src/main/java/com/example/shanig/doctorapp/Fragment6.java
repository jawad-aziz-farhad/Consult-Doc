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

public class Fragment6 extends Fragment {

    private Button addSpecialization,done;
    private TextView specialTitle,instituteName,year;
    private String getTitle,getInstitute,getYear;
    private DatabaseReference databaseReference;
    private FragmentManager fragmentManager;
    private Fragment profileFragment,fragment;

    public Fragment6(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Specializations");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment6, container, false);


        specialTitle = (TextView) fragmentLayout.findViewById(R.id.specializationTitle);
        instituteName = (TextView) fragmentLayout.findViewById(R.id.specializationInstitute);
        year = (TextView) fragmentLayout.findViewById(R.id.specializationYear);
        addSpecialization = (Button) fragmentLayout.findViewById(R.id.addSpecialization);
        done = (Button) fragmentLayout.findViewById(R.id.donespecial);

        addSpecialization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addSpecializations();

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

    private void addSpecializations(){

        getTitle = specialTitle.getText().toString().trim();
        getInstitute = instituteName.getText().toString().trim();
        getYear = year.getText().toString().trim();

        if(TextUtils.isEmpty(getTitle)){

            Toast.makeText(getActivity(),getResources().getString(R.string.specializationTitle),Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(getInstitute)){

            Toast.makeText(getActivity(),getResources().getString(R.string.instituteName),Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(getYear)){

            Toast.makeText(getActivity(),getResources().getString(R.string.completionYear),Toast.LENGTH_LONG).show();
        }
        else{

            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            specialize addSpecial = new specialize();
            addSpecial.setSpecializationTitle(getTitle);
            addSpecial.setSpecializationInstitute(getInstitute);
            addSpecial.setCompletionYear(getYear);
            databaseReference.child(userId).push().setValue(addSpecial);

            fragmentManager = getActivity().getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragment = new Fragment6();
            fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
            fragmentTransaction.commit();
        }
    }
}
