package com.pinch.android.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.astuetz.PagerSlidingTabStrip;
import com.facebook.FacebookSdk;
import com.pinch.android.R;
import com.pinch.android.adapters.EventsFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager mVPager;
    private EventsFragmentPagerAdapter mEventsPagerAdapter;
    private PagerSlidingTabStrip mTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setupViewPager();

    }

    private void setupViewPager() {
        mVPager = (ViewPager) findViewById(R.id.viewpager);
        mEventsPagerAdapter = new EventsFragmentPagerAdapter(getSupportFragmentManager(), this);
        mVPager.setAdapter(mEventsPagerAdapter);
        mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mTabStrip.setViewPager(mVPager);
    }
}
