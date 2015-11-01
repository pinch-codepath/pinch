package com.pinch.android.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;
import com.pinch.android.R;
import com.pinch.android.fragments.UserProfileBioFragment;
import com.pinch.android.fragments.UserProfileGoalsFragment;

import java.util.ArrayList;

public class UserProfilePagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    public static class UserProfileTabs {
        String title;
        int tabIcon;
        public UserProfileTabs(String title, int tabIcon) {
            this.title = title;
            this.tabIcon = tabIcon;
        }
    }

    public static final String BIO = "Bio";
    public static final String GOALS = "Goals";
    public ArrayList<UserProfileTabs> tabs = new ArrayList<>();

    public UserProfilePagerAdapter(FragmentManager fm) {
        super(fm);
        tabs.add(new UserProfileTabs(BIO, R.drawable.ic_tab_profile2));
        tabs.add(new UserProfileTabs(GOALS, R.drawable.ic_tab_calendar));
        tabs.add(new UserProfileTabs(GOALS, R.drawable.ic_tab_friends));
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new UserProfileBioFragment();
            case 1:
                return new UserProfileGoalsFragment();
            default:
                return new UserProfileBioFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).title;
    }

    @Override
    public int getPageIconResId(int position) {
        return tabs.get(position).tabIcon;
    }
}
