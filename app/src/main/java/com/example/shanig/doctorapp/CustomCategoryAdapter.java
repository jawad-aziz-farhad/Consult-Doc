package com.example.shanig.doctorapp;

/**
 * Created by ShaniG on 7/28/2017.
 */


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
public class CustomCategoryAdapter extends FragmentPagerAdapter{

    private static final String TAG = CustomCategoryAdapter.class.getSimpleName();

    private static final int FRAGMENT_COUNT = 1;

    public CustomCategoryAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new Fragment8();

        }
        return null;
    }

    @Override
    public int getCount() {
        return FRAGMENT_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Personal Details";
        }
        return null;
    }
}

