package com.example.shanig.doctorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class Fragment8 extends Fragment {

    private ArrayList<Users> itemsArrayList;
    private ListView listView1;
    private ArrayList<String> usersKeys;
    private ProgressDialog progressDialog;
    private String fname,lname,address,city,country,category,image,gender;
    private String key;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment8, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string._message));
        progressDialog.show();

        listView1 = (ListView) fragmentLayout.findViewById(R.id.allDoctors);
        Intent intent = getActivity().getIntent();
        String user = intent.getStringExtra("DocList");

        final Categories getCat = new Gson().fromJson(user,Categories.class);

        Log.w("VALUE :",getCat.getCategoryName());

        String userCat = getCat.getCategoryName();

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users");

        db.orderByChild("category").equalTo(userCat).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                itemsArrayList = new ArrayList<>();
                usersKeys = new ArrayList<String>();

                for (DataSnapshot childsnap : dataSnapshot.getChildren()) {
                    category = (String) childsnap.child("category").getValue();
                    fname = (String) childsnap.child("firstName").getValue();
                    lname = (String) childsnap.child("lastName").getValue();
                    address = (String) childsnap.child("address").getValue();
                    city = (String) childsnap.child("city").getValue();
                    country = (String) childsnap.child("country").getValue();
                    gender = (String) childsnap.child("gender").getValue();
                    image = (String) childsnap.child("imagePath").getValue();
                    key = childsnap.getKey();
                    itemsArrayList.add(new Users(fname, lname, address, city, country, gender, image, category));
                    usersKeys.add(key);
                }
                populateData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String getId = usersKeys.get(position);
                //Put the value
                Profile ldf = new Profile ();
                //Fragment3 frag = new Fragment3();
                Bundle args = new Bundle();
                args.putString("userKey", getId);
                args.putString("option","selected");
                ldf.setArguments(args);
                //frag.setArguments(args);
                //Inflate the fragment
                getFragmentManager().beginTransaction().add(R.id.main_container_wrapper, ldf).commit();

            }
        });

        return fragmentLayout;

    }

    private void populateData(){
        progressDialog.dismiss();
        CustomDoctorAdapter adapter = new CustomDoctorAdapter(getActivity(), itemsArrayList);
        listView1.setAdapter(adapter);
    }
}
