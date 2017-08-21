package com.example.shanig.doctorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ShaniG on 7/19/2017.
 */

public class Profile extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String userId,userType;
    private String checkType="Doctor";
    private CircleImageView circleImageView;
    private Button appointment;
    private TextView UserName,Category;
    private ProgressDialog progressDialog;
    private static final int RESULT_OK = -1;
    private int PICK_IMAGE_REQUEST = 111;
    private Uri filePath;
    StorageReference storageRef,childRef,temp;
    DatabaseReference db;
    ArrayList<String> pop;
    private ArrayList<String> arrayList = new ArrayList<>();
    private String getexperience,getcontact,getaddress,getcity,getcountry,gen,utype,date,uId,first,last,getCategory,imagePath;
    private double lati,longi;
    private String selectedDoctor;

    public Profile() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait....");
        db = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        circleImageView =(CircleImageView) view.findViewById(R.id.ProfileImage);
        UserName = (TextView) view.findViewById(R.id.UserName);
        Category = (TextView) view.findViewById(R.id.UserCategory);
        appointment = (Button) view.findViewById(R.id.appointmentButton);
        tabLayout = (TabLayout) view.findViewById(R.id.profileTab);
        viewPager = (ViewPager) view.findViewById(R.id.profileDetails);
        Bundle bundle = new Bundle();
        bundle.putString("option" ,getArguments().getString("option"));
        bundle.putString("userKey" ,getArguments().getString("userKey"));
        viewPager.setAdapter(new CustomProfileAdapter(getChildFragmentManager(), bundle));
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://doctorapp-27b06.appspot.com/Users");    //change the url according to your firebase app
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(
                getResources().getColor(R.color.black),
                getResources().getColor(R.color.white)
        );

        return view;
    }


    private void selected(){

        db.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Users user = dataSnapshot.getValue(Users.class);
                UserName.setText(user.getFirstName() + " " + user.getLastName());
                userType = user.getUserType().toString();
                if (user.getCategory() != null) {
                    Category.setText(user.getCategory());
                } else {
                    Category.setText(getResources().getString(R.string.noRecord));
                }
                appointment.setText(getResources().getString(R.string.appointment));
                getAppointment();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void ownProfile(){

        db.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Users user = dataSnapshot.getValue(Users.class);
                UserName.setText(user.getFirstName() + " " + user.getLastName());
                userType = user.getUserType().toString();
                if(user.getCategory()!=null){
                    Category.setText(user.getCategory());
                }
                else{
                    Category.setText(getResources().getString(R.string.noRecord));
                }
                appointment.setText(getResources().getString(R.string.checkappointment));
                checkAppointment();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        setProfileImage();
        profileImage();

    }
    private void setProfileImage() {

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                //Setting image to ImageView
                circleImageView.setImageBitmap(bitmap);
                if(filePath != null) {

                    progressDialog.show();

                    //uploading the image
                    UploadTask uploadTask = childRef.putFile(filePath);

                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.cancel();
                            Toast.makeText(getActivity(), "Upload successful", Toast.LENGTH_SHORT).show();
                            childRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Got the download URL for 'users/me/profile.png'
                                    @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    imagePath = downloadUrl.toString(); /// The string(file link) that you need

                                    db.child(userId).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            Users user = dataSnapshot.getValue(Users.class);
                                            gen= user.getGender();
                                            utype = user.getUserType();
                                            date = user.getDob();
                                            first = user.getFirstName();
                                            last = user.getLastName();
                                            lati = user.getLocationLati();
                                            longi = user.getLocationLongi();
                                            getexperience = user.getExperience();
                                            getcontact = user.getContact();
                                            getaddress = user.getAddress();
                                            getcity = user.getCity();
                                            getcountry = user.getCountry();
                                            getCategory = user.getCategory();

                                            Users newUser = new Users(first,last,gen,utype,getexperience,getcontact,getaddress,getcity,getcountry,date,getCategory,lati,longi,imagePath);
                                            db.child(userId).setValue(newUser);

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError error) {
                                            // Failed to read value
                                            Log.w("Failure", "Failed to read value.", error.toException());
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle any errors
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            progressDialog.cancel();
                            Toast.makeText(getActivity(), "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "Select an image", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getAppointment(){

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"Appointment Set",Toast.LENGTH_LONG).show();

            }
        });
    }
    private void checkAppointment(){

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),"Appointment Checked",Toast.LENGTH_LONG).show();

            }
        });
    }
    /* private void userTypeCheck(){

         if(userType==checkType){
             appointment.setText(getResources().getString(R.string.checkappointment));
             checkAppointment();
         }
         else{
             appointment.setText(getResources().getString(R.string.appointment));
             getAppointment();
         }
     }*/
    private void profileImage(){
        childRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity()).load(uri).into(circleImageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                circleImageView.setImageResource(R.drawable.ic_account_circle);
            }
        });
    }

}
