package com.example.shanig.doctorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class list extends AppCompatActivity {

    ArrayList<articles> itemsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

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
                for (DataSnapshot childsnap : dataSnapshot.getChildren()) {
                    String title = (String) childsnap.child("title").getValue();
                    String image = (String) childsnap.child("image").getValue();
                    String description = (String) childsnap.child("description").getValue();
                    String time = (String) childsnap.child("time").getValue();
                    articles addarticle = new articles(title,description,image, time);
                    itemsArrayList.add(addarticle);
                }

                populateData();
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
    }
    private void populateData() {
        CustomListAdapter adapter = new CustomListAdapter(list.this, itemsArrayList);
        ListView itemsListView  = (ListView) findViewById(R.id.articles);
        itemsListView.setAdapter(adapter);
    }
}
