package com.example.shanig.doctorapp;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WeeklyTimings extends Dialog implements View.OnClickListener {

    private TextView mon,tue,wed,thur,fri,sat,sun;
    private DatabaseReference weekTiming;
    private String userId;
    private String getDays,getkey;
    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<String> timeKeys = new ArrayList<>();

    public WeeklyTimings(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_timings);

        mon = (TextView) findViewById(R.id.monday);
        tue = (TextView) findViewById(R.id.tuesday);
        wed = (TextView) findViewById(R.id.wednesday);
        thur = (TextView) findViewById(R.id.thursday);
        fri = (TextView) findViewById(R.id.friday);
        sat = (TextView) findViewById(R.id.saturday);
        sun = (TextView) findViewById(R.id.sunday);
        weekTiming = FirebaseDatabase.getInstance().getReference().child("Timings");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        weekTiming.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {

                   for (DataSnapshot childSnap : dataSnapshot.getChildren()) {
                        getDays = childSnap.getValue().toString();
                        getkey = childSnap.getKey().toString();
                        timeKeys.add(getkey);
                        time.add(getDays);
                        }

                    populateTime();
                }
                else{
                    time.add(getContext().getResources().getString(R.string.noRecord));
                    timeKeys.add(getkey);
                    populateTime();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("ERROR: ", "Failed to read value.", error.toException());
            }
        });


    }

private void populateTime() {

        for(int i=0;i<timeKeys.size();i++){
            if(i==0) {
                if (timeKeys.get(i).equals("Monday")) {

                    mon.append("\n" +timeKeys.get(i) + "  " + time.get(i));
                }
                else if (timeKeys.get(i).equals("Tuesday")){

                    tue.append("\n" +timeKeys.get(i) + "  " + time.get(i));
                }
                else if (timeKeys.get(i).equals("Wednesday")){

                    wed.append("\n" +timeKeys.get(i) + "  " + time.get(i));
                }
                else if (timeKeys.get(i).equals("Thursday")){

                    thur.append("\n" +timeKeys.get(i) + "  " + time.get(i));
                }
                else if (timeKeys.get(i).equals("Friday")){

                    fri.append("\n" +timeKeys.get(i) + "  " + time.get(i));
                }
                else if (timeKeys.get(i).equals("Saturday")){

                    sat.append("\n" +timeKeys.get(i) + "  " + time.get(i));
                }
                else if (timeKeys.get(i).equals("Sunday")){

                    sun.append("\n" +timeKeys.get(i) + "  " + time.get(i));
                }
            }
            else {
                if (timeKeys.get(i).equals("Monday")) {

                    mon.append("\n" + timeKeys.get(i) + "  " + time.get(i));
                }
                else if (timeKeys.get(i).equals("Tuesday")){

                    tue.append("\n" + timeKeys.get(i) + "  " + time.get(i));
                }
                else if (timeKeys.get(i).equals("Wednesday")){

                    wed.append("\n" + timeKeys.get(i) + "  " + time.get(i));
                }
                else if (timeKeys.get(i).equals("Thursday")){

                    thur.append("\n" + timeKeys.get(i) + "  " + time.get(i));
                }
                else if (timeKeys.get(i).equals("Friday")){

                    //fri.append("\n" + timeKeys.get(i) + "  " + time.get(i));
                }
                else if (timeKeys.get(i).equals("Saturday")){

                    sat.append("\n" + timeKeys.get(i) + "  " + time.get(i));
                }
                else if (timeKeys.get(i).equals("Sunday")){

                    sun.append("\n" + timeKeys.get(i) + "  " + time.get(i));
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
        populateTime();
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }
}
