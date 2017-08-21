package com.example.shanig.doctorapp;

/**
 * Created by ShaniG on 7/28/2017.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
public class CustomProfileAdapter extends FragmentPagerAdapter {

    private static final String TAG = CustomProfileAdapter.class.getSimpleName();

    private static final int FRAGMENT_COUNT = 2;
    private Bundle bundle;

    public CustomProfileAdapter(FragmentManager fm , Bundle bundle) {
        super(fm);

        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Fragment3 fragment3 = new Fragment3();
                Bundle _bundle = new Bundle();
                _bundle.putString("userKey", bundle.getString("userKey"));
                _bundle.putString("option", bundle.getString("option"));
                fragment3.setArguments(_bundle);
                return fragment3;
            case 1:
                return new Fragment4();
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
            case 1:
                return "Services";
        }
        return null;
    }

}

