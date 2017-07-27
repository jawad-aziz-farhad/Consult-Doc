package com.example.shanig.doctorapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by ShaniG on 7/19/2017.
 */

public class Fragment2 extends Fragment {

    private GridView gridView;
    private ArrayList<articles> itemsArrayList;
    public Fragment2(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseReference db= FirebaseDatabase.getInstance().getReference();

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                itemsArrayList= new ArrayList<>();


                for (DataSnapshot childsnap : dataSnapshot.getChildren()) {
                    String title = (String) childsnap.child("title").getValue();
                    String image = (String) childsnap.child("image").getValue();
                    String description = (String) childsnap.child("description").getValue();
                    String time = (String) childsnap.child("time").getValue();
                    itemsArrayList.add(new articles(title,description,image, time));

                }

                populateData();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

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

                articles article = itemsArrayList.get(position);
                Gson gson = new Gson();
                String article_data = gson.toJson(article);
                Intent intent = new Intent(getActivity(),article_detail.class);
                intent.putExtra("article",article_data);
                startActivity(intent);
            }
        });


        return fragmentLayout;
    }


    private void populateData(){

        CustomGridAdapter adapter = new CustomGridAdapter(getActivity(), itemsArrayList);
        gridView.setAdapter(adapter);

    }
}
