package com.example.shanig.doctorapp;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Timing extends navDrawer {

    private EditText start,end;
    private Spinner day,startTime,endTime;
    private String getDay,getStart,getEnd,getStartTime,getEndTime;
    private String userId;
    private DatabaseReference setTime;
    private Button addTime,done;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Timing.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_timing, null, false);
        drawer.addView(contentView, 0);

        start = (EditText) findViewById(R.id.from);
        end = (EditText) findViewById(R.id.to);
        day = (Spinner) findViewById(R.id.days);
        startTime = (Spinner) findViewById(R.id.fromTime);
        endTime = (Spinner) findViewById(R.id.toTime);
        addTime = (Button) findViewById(R.id.addTime);
        done = (Button) findViewById(R.id.doneTime);
        setTime = FirebaseDatabase.getInstance().getReference().child("Timings");

        start.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                setStartingHours();
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                setEndingHours();
            }
        });

       addTime.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               setTiming();
           }
       });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager = getSupportFragmentManager();
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment profileFragment = new Profile();
                Bundle args = new Bundle();
                args.putString("userKey", FirebaseAuth.getInstance().getCurrentUser().getUid());
                profileFragment.setArguments(args);
                fragmentTransaction.replace(R.id.main_container_wrapper, profileFragment);
                fragmentTransaction.commit();

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setStartingHours() {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(Timing.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                start.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setEndingHours(){

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Timing.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        end.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
        }

    private void setTiming(){

        getDay = day.getSelectedItem().toString();
        getStart = start.getText().toString().trim();
        getEnd = end.getText().toString().trim();
        getStartTime = startTime.getSelectedItem().toString();
        getEndTime = endTime.getSelectedItem().toString();

                if(TextUtils.isEmpty(getDay)){

                    Toast.makeText(Timing.this, getResources().getString(R.string.days), Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(getStart)){

                    Toast.makeText(Timing.this, getResources().getString(R.string.starting), Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(getEnd)){

                    Toast.makeText(Timing.this, getResources().getString(R.string.ending), Toast.LENGTH_SHORT).show();
                }
                else{

                    userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    SetTimings userTime = new SetTimings();
                    userTime.setStart(getStart);
                    userTime.setEnd(getEnd);
                    String saveStart = userTime.getStart() + " " + getStartTime + " - " + userTime.getEnd() + " " + getEndTime;
                    String saveEnd = userTime.getEnd() + " " + getEndTime;
                    setTime.child(userId).child(getDay).setValue(saveStart);
                    //setTime.child(getDay).child(userId).child("endingTime").setValue(saveEnd);
                    Toast.makeText(Timing.this, getResources().getString(R.string.added), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Timing.this,Timing.class));
                }
    }
}
