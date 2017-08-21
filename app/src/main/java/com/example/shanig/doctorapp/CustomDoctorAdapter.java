package com.example.shanig.doctorapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
/**
 * Created by ShaniG on 7/25/2017.
 */
public class CustomDoctorAdapter extends BaseAdapter {

    private Context context; //context
    private ArrayList<Users> items; //data source of the list adapter

    //public constructor

    public CustomDoctorAdapter(Context context, ArrayList<Users> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.doctorinfo, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Users currentItem = (Users) getItem(position);
        viewHolder.docName.setText(currentItem.getFirstName() + " " + currentItem.getLastName());
        viewHolder.docCat.setText(currentItem.getGender());
        viewHolder.docCategory.setText(currentItem.getCategory());
        viewHolder.docAddres.setText(currentItem.getAddress() + "," + currentItem.getCity() + "," + currentItem.getCountry());
        Glide.with(context).load(currentItem.getImagePath()).into(viewHolder.docImg);
        return convertView;
    }

    //ViewHolder inner class
    private class ViewHolder {
        TextView docName;
        TextView docCat;
        TextView docAddres;
        TextView docCategory;
        ImageView docImg;

        public ViewHolder(View view) {
            docName = (TextView)
                    view.findViewById(R.id.docName);
            docCategory = (TextView)
                    view.findViewById(R.id.docCat);

            docCat = (TextView)
                    view.findViewById(R.id.docCategory);
            docAddres = (TextView)
                    view.findViewById(R.id.docAddress);
            docImg= (ImageView) view.findViewById(R.id.docImage);

        }
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.article_items, parent, false);
        }

        // get current item to be displayed
        articles currentItem = items.get(position);

        // get the TextView for item name and item description
        TextView articleTitle = (TextView)
                convertView.findViewById(R.id.text_view_item_name);
        TextView articleTime = (TextView)
                convertView.findViewById(R.id.text_view_item_description);
        ImageView articleImage= (ImageView) convertView.findViewById(R.id.articleImg);

        articleTitle.setText(currentItem.getTitle());
        articleTime.setText(currentItem.getTime());
        Picasso.with(context).load(currentItem.getImage()).into(articleImage);

        // returns the view for the current row
        return convertView;
    }*/

}
