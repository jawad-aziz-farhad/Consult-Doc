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

public class Fragment1 extends Fragment {

    private ArrayList<articles> itemsArrayList;
    private ListView listView1;
    private ArrayList<String> articleKeys;
    private ProgressDialog progressDialog;

    public Fragment1() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getString(R.string.app_name));
        progressDialog.setMessage(getString(R.string._message));
        progressDialog.show();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                itemsArrayList = new ArrayList<>();
                articleKeys = new ArrayList<>();

                for (DataSnapshot childsnap : dataSnapshot.getChildren()) {
                    String title = (String) childsnap.child("title").getValue();
                    String image = (String) childsnap.child("image").getValue();
                    String description = (String) childsnap.child("description").getValue();
                    String time = (String) childsnap.child("time").getValue();
                    String articleId = (String) childsnap.child("id").getValue();
                    itemsArrayList.add(new articles(title, description, image, time));
                    articleKeys.add(articleId);
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
        View fragmentLayout = inflater.inflate(R.layout.fragment1, container, false);

        listView1 = (ListView) fragmentLayout.findViewById(R.id.articles);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                articles article = itemsArrayList.get(position);

                String getId = articleKeys.get(position);
                Gson gson = new Gson();
                String article_data = gson.toJson(article);
                Intent intent = new Intent(getActivity(), article_detail.class);
                intent.putExtra("article", article_data);
                startActivity(intent);
            }
        });

        return fragmentLayout;
    }

    private void populateData() {
        progressDialog.dismiss();
        CustomListAdapter adapter = new CustomListAdapter(getActivity(), itemsArrayList);
        listView1.setAdapter(adapter);
    }
}
