package com.example.shanig.doctorapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class navDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private Fragment fragment = null,profileFragment;
    private TextView showEmail,loggedIn;
    private ImageView profileImage;
    NavigationView navigationView;
    protected DrawerLayout drawer;
    private DatabaseReference navUserProfile;
    private StorageReference storageRef,childRef;
    private String userId;
    private String uType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navUserProfile = FirebaseDatabase.getInstance().getReference().child("Users");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        showEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.loggedInUserEmail);
        profileImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.navImage);
        loggedIn = (TextView) navigationView.getHeaderView(0).findViewById(R.id.loggedInUserName);
        showEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        navProfile();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            startActivity(new Intent(navDrawer.this,navigation.class));

        } else if (id == R.id.nav_profile) {

            fragmentManager = getSupportFragmentManager();
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            profileFragment = new Profile();
            Bundle args = new Bundle();
            args.putString("userKey", FirebaseAuth.getInstance().getCurrentUser().getUid());
            args.putString("option","normal");
            profileFragment.setArguments(args);
            fragmentTransaction.replace(R.id.main_container_wrapper, profileFragment);
            fragmentTransaction.commit();


        } else if (id == R.id.nav_status) {

        } else if (id == R.id.nav_articles) {

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_signout) {

            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void signOut() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Sign Out");
        alertDialogBuilder
                .setMessage("Are you sure to sign out?")
                .setCancelable(false)
                .setPositiveButton("Sign out", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(navDrawer.this, SplashScreen.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    private void navProfile(){

        navUserProfile.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Users userProfile = dataSnapshot.getValue(Users.class);
                String first = userProfile.getFirstName();
                String last = userProfile.getLastName();
                uType = userProfile.getUserType();
                loggedIn.setText(first + " " + last);
                if(uType.equals("Patient")){

                    navigationView.getMenu().findItem(R.id.nav_profile).setVisible(false);
                }
                profileImage();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void profileImage(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://doctorapp-27b06.appspot.com/Users");
        childRef = storageRef.child(userId);
        if(uType.equals("Patient")){

            profileImage.setImageResource(R.drawable.logo);
        }
        else {
            childRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(navDrawer.this).load(uri).into(profileImage);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    profileImage.setImageResource(R.drawable.ic_account_circle);
                }
            });
        }
    }
}
