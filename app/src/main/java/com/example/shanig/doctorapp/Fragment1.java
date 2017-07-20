package com.example.shanig.doctorapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by ShaniG on 7/19/2017.
 */

public class Fragment1 extends Fragment {

    private ListView display;
    String me="HERE";
    private ArrayList<String> articleDetails=new ArrayList<>();

    public Fragment1(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentLayout = inflater.inflate(R.layout.fragment1, container, false);

        ListView listView1 = (ListView) fragmentLayout.findViewById(R.id.articles);
        final ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, articleDetails);
        listView1.setAdapter(itemsAdapter);
        articleDetails.add(me);
        return fragmentLayout;


    }
}
