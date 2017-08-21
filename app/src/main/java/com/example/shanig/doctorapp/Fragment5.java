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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.w3c.dom.Text;

public class Fragment5 extends Fragment {

    private Button addDegree,done;
    private EditText degreeTitle,instituteName,year;
    private String getTitle,getInstitute,getYear;
    private DatabaseReference databaseReference;
    private FragmentManager fragmentManager;
    private Fragment profileFragment,fragment;

    public Fragment5(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Degrees");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentLayout = inflater.inflate(R.layout.fragment5, container, false);


        degreeTitle = (EditText) fragmentLayout.findViewById(R.id.degreeTitle);
        instituteName = (EditText) fragmentLayout.findViewById(R.id.degreeInstitue);
        year = (EditText) fragmentLayout.findViewById(R.id.degreeYear);
        addDegree = (Button) fragmentLayout.findViewById(R.id.addDegree);
        done = (Button) fragmentLayout.findViewById(R.id.donedegree);

        addDegree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDegrees();
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

    private void addDegrees(){

                getTitle = degreeTitle.getText().toString();
                getInstitute = instituteName.getText().toString();
                getYear = year.getText().toString();

                if(TextUtils.isEmpty(getTitle)){

                    Toast.makeText(getActivity(),getResources().getString(R.string.degreeTitle),Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(getInstitute)){

                    Toast.makeText(getActivity(),getResources().getString(R.string.instituteName),Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(getYear)){

                    Toast.makeText(getActivity(),getResources().getString(R.string.completionYear),Toast.LENGTH_LONG).show();
                }
                else{

                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    degree addDegree = new degree();
                    addDegree.setDegreeTitle(getTitle);
                    addDegree.setDegreeInstitute(getInstitute);
                    addDegree.setCompletionYear(getYear);
                    databaseReference.child(userId).push().setValue(addDegree);
                    Toast.makeText(getActivity(),getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
                    fragmentManager = getActivity().getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragment = new Fragment5();
                    fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
                    fragmentTransaction.commit();
                }
    }
}
