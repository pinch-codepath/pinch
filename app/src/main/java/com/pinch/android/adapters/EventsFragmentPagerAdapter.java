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

public class EventsFragmentPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider{

    public static final String SIGN_UPS = "Sign ups";
    public static final String FAVORITES = "Favorites";
    public static final String SEARCH = "Search";
    public static final String CREATE_EVENT = "Create Event";
    public static final String PROFILE = "Profile";

    private class Tab {
        public Tab(String title, int tabIcon, int materialTabIcons) {
            this.title = title;
            this.tabIcon = tabIcon;
            this.materialTabIcons = materialTabIcons;
        }
        String title;
        int tabIcon;
        int materialTabIcons;
    }

    ArrayList<Tab> tabs = new ArrayList<>();
    private Context context;

    public EventsFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        tabs.add(new Tab(SEARCH, R.drawable.ic_tab_calendar, R.drawable.ic_tab_calendar));
        tabs.add(new Tab(SIGN_UPS, R.drawable.ic_tab_signups, R.drawable.ic_material_events));
        tabs.add(new Tab(FAVORITES, R.drawable.ic_tab_favorites_filled, R.drawable.ic_tab_favorites_filled));
        tabs.add(new Tab(CREATE_EVENT, R.drawable.ic_tab_create_event, R.drawable.ic_material_create_event));
        tabs.add(new Tab(PROFILE, R.drawable.ic_tab_profile, R.drawable.ic_material_profile));
    }

    @Override
    public int getCount() {
        return tabs.size();
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
//        return materialTabIcons[i];
        return tabs.get(position).tabIcon;
    }
}
