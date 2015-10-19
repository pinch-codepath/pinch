package com.pinch.android.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.pinch.android.R;
import com.pinch.android.fragments.EventsFragment;
import com.pinch.android.fragments.ProfileFragment;
import com.pinch.android.fragments.SearchFragment;

public class EventsFragmentPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider{

    private String tabTitles[] = new String[] { "Events", "Search", "Favorites", "Sign ups", "Profile" };
    private int tabIcons[] = new int[] {
            R.drawable.ic_tab_calendar,
            R.drawable.ic_tab_search,
            R.drawable.ic_tab_favorites_filled,
            R.drawable.ic_tab_signups,
            R.drawable.ic_tab_profile,
    };
    private Context context;

    public EventsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return tabIcons.length;
//        return tabTitles.length;
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
            return new ProfileFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public int getPageIconResId(int i) {
        return tabIcons[i];
    }
}
