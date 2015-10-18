package com.pinch.android.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pinch.android.fragments.EventsFragment;
import com.pinch.android.fragments.SearchFragment;

public class EventsFragmentPagerAdapter extends FragmentPagerAdapter{

    private String tabTitles[] = new String[] { "Events", "Search" };
    private Context context;

    public EventsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return new EventsFragment();
        }
        else if(position == 1) {
            return new SearchFragment();
        }
        else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
