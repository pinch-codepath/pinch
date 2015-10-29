package com.pinch.android.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.pinch.android.R;
import com.pinch.android.fragments.EventCreateFragment;
import com.pinch.android.fragments.OpenEventsFragment;
import com.pinch.android.fragments.SearchFragment;
import com.pinch.android.fragments.SignedUpEventsFragment;
import com.pinch.android.fragments.UserProfileFragment;

public class EventsFragmentPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider{

    private String tabTitles[] = new String[] { "Search", "Sign ups", "Create Event", "Profile" };
    private int tabIcons[] = new int[] {
            R.drawable.ic_tab_calendar,
            R.drawable.ic_tab_signups,
            R.drawable.ic_tab_create_event,
            R.drawable.ic_tab_profile,
    };
    private int materialTabIcons[] = new int[] {
            R.drawable.ic_tab_calendar,     //should be changed to logo 'P'
            R.drawable.ic_material_events,
            R.drawable.ic_material_create_event,
            R.drawable.ic_material_profile,
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
            return new SearchFragment();
        }
        else if(position == 1) {
            return new SignedUpEventsFragment();
        }
        else if(position == 2) {
            return new EventCreateFragment();
        }
        else if(position == 3) {
            return new UserProfileFragment();
        }
        else {
            return new Fragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public int getPageIconResId(int i) {
//        return materialTabIcons[i];
        return tabIcons[i];
    }
}
