package com.example.shanig.doctorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import de.hdodenhof.circleimageview.CircleImageView;

public class message extends AppCompatActivity {

    CircleImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        back = (CircleImageView) findViewById(R.id.goBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(message.this,SignIn.class));
            }
        });
    }
}
