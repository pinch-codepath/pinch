package com.pinch.android.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.pinch.android.R;
import com.pinch.android.fragments.EventCreateFragment;
import com.pinch.android.fragments.EventsForFollowedOrgFragment;
import com.pinch.android.fragments.SearchFragment;
import com.pinch.android.fragments.SignedUpEventsFragment;
import com.pinch.android.fragments.UserProfileFragment;

import java.util.ArrayList;

public class EventsFragmentPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    public static final String SIGN_UPS = "Sign ups";
    public static final String FAVORITES = "Favorites";
    public static final String SEARCH = "Search";
    public static final String CREATE_EVENT = "Create Event";
    public static final String PROFILE = "Profile";
    public ArrayList<Tab> tabs = new ArrayList<>();
    private Context context;
    public EventsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabs.add(new Tab(SEARCH, R.drawable.ic_tab_calendar, R.drawable.ic_material_home));
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public int getItemPosition(Object o) {
        if (o instanceof SearchFragment) {
            return 0;
        }
        return tabs.indexOf(o);
    }

    @Override
    public Fragment getItem(int position) {
        Tab tab = tabs.get(position);
        switch (tab.title) {
            case SEARCH:
                return new SearchFragment();
            case FAVORITES:
                return new EventsForFollowedOrgFragment();
            case SIGN_UPS:
                return new SignedUpEventsFragment();
            case CREATE_EVENT:
                return new EventCreateFragment();
            case PROFILE:
                return new UserProfileFragment();
            default:
                return new Fragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabs.get(position).title;
    }

    @Override
    public int getPageIconResId(int position) {
        return tabs.get(position).materialTabIcons;
//        return tabs.get(position).tabIcon;
    }

    public static class Tab {
        String title;
        int tabIcon;
        int materialTabIcons;
        public Tab(String title, int tabIcon, int materialTabIcons) {
            this.title = title;
            this.tabIcon = tabIcon;
            this.materialTabIcons = materialTabIcons;
        }
    }
}
