package com.example.shanig.doctorapp;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

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

public class Fragment2 extends Fragment {

    private GridView gridView;
    private FragmentManager fragmentManager;
    private Fragment fragment = null;
    private ArrayList<Categories> itemsArrayList = new ArrayList<>();
    private ProgressDialog progressDialog;
    public Fragment2(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(getActivity());
        //progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string._message));
        progressDialog.show();


        DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Categories");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot categorySnap : dataSnapshot.getChildren()) {
                        String title = categorySnap.child("title").getValue(String.class);
                        String icon = categorySnap.child("icon").getValue(String.class);
                        itemsArrayList.add(new Categories(title,icon));
                }
                populateData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment2, container, false);
        gridView = (GridView) fragmentLayout.findViewById(R.id.grid_view);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Categories categories = itemsArrayList.get(position);
                Gson gson = new Gson();
                String category_data = gson.toJson(categories);
                Intent intent = new Intent(getActivity(),DoctorCategory.class);
                intent.putExtra("DocList",category_data);
                startActivity(intent);

            }
        });


        return fragmentLayout;
    }


    private void populateData(){
        progressDialog.cancel();
        CustomGridAdapter adapter = new CustomGridAdapter(getActivity(), itemsArrayList);
        gridView.setAdapter(adapter);

    }
}
