package com.pinch.android.activities;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.facebook.FacebookSdk;
import com.pinch.android.PinchApplication;
import com.pinch.android.R;
import com.pinch.android.adapters.EventsFragmentPagerAdapter;
import com.pinch.android.dialogs.FacebookLoginDialog;
import com.pinch.android.events.AddOrgTabsEvent;
import com.pinch.android.events.AddUserTabsEvent;
import com.pinch.android.events.RemoveUserTabsEvent;
import com.pinch.android.service.RegistrationIntentService;
import com.squareup.otto.Subscribe;

import static com.pinch.android.adapters.EventsFragmentPagerAdapter.CREATE_EVENT;
import static com.pinch.android.adapters.EventsFragmentPagerAdapter.FAVORITES;
import static com.pinch.android.adapters.EventsFragmentPagerAdapter.PROFILE;
import static com.pinch.android.adapters.EventsFragmentPagerAdapter.SIGN_UPS;
import static com.pinch.android.adapters.EventsFragmentPagerAdapter.Tab;

public class MainActivity extends AppCompatActivity implements FacebookLoginDialog.FacebookLoginDialogListener {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private ViewPager mVPager;
    private EventsFragmentPagerAdapter mEventsPagerAdapter;
    private PagerSlidingTabStrip mTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        // load user if logged in.
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setupViewPager();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        PinchApplication pinchApplication = ((PinchApplication) getApplication());
        pinchApplication.bus.register(this);
        pinchApplication.getUser();
    }

    @Subscribe
    public void addUserTabs(AddUserTabsEvent event) {
        mEventsPagerAdapter.tabs.add(1, new Tab(SIGN_UPS, R.drawable.ic_tab_signups, R.drawable.ic_material_events));
        mEventsPagerAdapter.tabs.add(2, new Tab(FAVORITES, R.drawable.ic_tab_favorites_filled, R.drawable.ic_tab_favorites_filled));
        mEventsPagerAdapter.tabs.add(3, new Tab(PROFILE, R.drawable.ic_tab_profile, R.drawable.ic_material_profile));
        mEventsPagerAdapter.notifyDataSetChanged();
        mTabStrip.notifyDataSetChanged();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("MainActivity", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Subscribe
    public void addOrgTabs(AddOrgTabsEvent event) {
        mEventsPagerAdapter.tabs.add(4, new Tab(CREATE_EVENT, R.drawable.ic_tab_create_event, R.drawable.ic_material_create_event));
        mEventsPagerAdapter.notifyDataSetChanged();
        mTabStrip.notifyDataSetChanged();
    }

    @Subscribe
    public void removeUserTabs(RemoveUserTabsEvent event) {
        while (mEventsPagerAdapter.tabs.size() > 1) {
            mEventsPagerAdapter.tabs.remove(1);
        }
        mEventsPagerAdapter.notifyDataSetChanged();
        mTabStrip.notifyDataSetChanged();
    }

    private void setupViewPager() {
        mVPager = (ViewPager) findViewById(R.id.viewpager);
        mEventsPagerAdapter = new EventsFragmentPagerAdapter(getSupportFragmentManager(), this);
        mVPager.setAdapter(mEventsPagerAdapter);
        mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mTabStrip.setViewPager(mVPager);
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
    }
}
