package com.example.shanig.doctorapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class article_detail extends AppCompatActivity {

    private TextView title,date,description;
    private ImageView articleImg;
    private CollapsingToolbarLayout layoutImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        layoutImage = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        title = (TextView) findViewById(R.id.articleTitle);
        /*date = (TextView) findViewById(R.id.articleDate);*/
        description = (TextView) findViewById(R.id.articleText);
        articleImg = (ImageView) findViewById(R.id.art);
        Intent intent = getIntent();
        String article = intent.getStringExtra("article");

        articles getArticle = new Gson().fromJson(article,articles.class);

        Log.w("VALUE :",getArticle.getDescript());

        String articleTitle = getArticle.getTitle();
        String articleDate = getArticle.getTime();
        String articleDes = getArticle.getDescript();


        title.setText(articleTitle);
        /*date.setText(articleDate);*/
        description.setText(articleDes);
        Glide.with(article_detail.this).load(getArticle.getImage()).into(articleImg);

    }
}
