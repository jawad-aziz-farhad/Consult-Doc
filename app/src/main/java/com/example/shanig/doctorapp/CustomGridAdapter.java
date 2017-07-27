package com.example.shanig.doctorapp;

/**
 * Created by ShaniG on 7/26/2017.
 */
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomGridAdapter extends BaseAdapter{
    private Context context; //context
    private ArrayList<articles> items; //data source of the list adapter
    public CustomGridAdapter(Context context, ArrayList<articles> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_items, parent, false);
            grid = new ViewHolder(convertView);
            convertView.setTag(grid);
        } else {
            grid = (CustomGridAdapter.ViewHolder) convertView.getTag();
        }

        articles currentItem = (articles) getItem(position);
        grid.articleTitle.setText(currentItem.getTitle());
        Glide.with(context).load(currentItem.getImage()).into(grid.articleImage);

        return convertView;
    }

    public class ViewHolder {
        TextView articleTitle;
        ImageView articleImage;

        public ViewHolder(View view) {
            articleTitle = (TextView)
                    view.findViewById(R.id.grid_text);
            articleImage= (ImageView) view.findViewById(R.id.grid_image);

        }
    }
}


