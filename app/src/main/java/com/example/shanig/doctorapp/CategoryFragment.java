package com.example.shanig.doctorapp;

/**
 * Created by ShaniG on 7/28/2017.
 */
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class CategoryFragment extends Fragment {

    private static final String TAG = CategoryFragment.class.getSimpleName();

    private TabLayout tabLayout;
    private ViewPager viewPager;


    public CategoryFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categoryfragment, container, false);

        tabLayout = (TabLayout)view.findViewById(R.id.tabs_doc);
        viewPager = (ViewPager)view.findViewById(R.id.view_pager);

        viewPager.setAdapter(new CustomCategoryAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(
                getResources().getColor(R.color.black),
                getResources().getColor(R.color.white)
        );
        //      setIcons();

        return view;
    }
/*
    private void setIcons(){

        final int[] tabicons = new int[]{

                R.drawable.article_icon,
                R.mipmap.doctor


        };

        tabLayout.getTabAt(0).setIcon(tabicons[0]);
        tabLayout.getTabAt(1).setIcon(tabicons[1]);

    }
*/

}
