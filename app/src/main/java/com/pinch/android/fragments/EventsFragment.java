package com.pinch.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.pinch.android.PinchApplication;
import com.pinch.android.R;
import com.pinch.android.Utils;
import com.pinch.android.activities.EventDetailsActivity;
import com.pinch.android.adapters.EventsArrayAdapter;
import com.pinch.android.util.Network;
import com.pinch.backend.eventEndpoint.model.Event;
import com.pinch.backend.userEndpoint.model.User;

import java.util.ArrayList;

import static com.pinch.android.util.FacebookUtil.openFacebookLoginDialog;

public abstract class EventsFragment extends Fragment {

    protected View mFragmentView;
    protected ArrayList<Event> mEventsArray;
    protected EventsArrayAdapter mEventsAdapter;
    protected ListView mLvEvents;

    protected Boolean checkAccessToken = false;

    protected SwipeRefreshLayout mSwipeContainer;

    public EventsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_events, container, false);
        if (checkAccessToken && AccessToken.getCurrentAccessToken() == null) {
            openFacebookLoginDialog(getActivity().getSupportFragmentManager());
        } else {
            setupFragment();
        }
        return mFragmentView;
    }

    protected void setupFragment() {
        setupViewObjects();
        setupSwipeToRefresh();
        populateEvents();
    }

    abstract void populateEvents();

    protected void setupViewObjects() {
        mLvEvents = (ListView) mFragmentView.findViewById(R.id.lvEvents);
        mEventsArray = new ArrayList<>();
        mEventsAdapter = new EventsArrayAdapter(getActivity(), mEventsArray);
        mLvEvents.setAdapter(mEventsAdapter);
        mLvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                Event e = mEventsArray.get(position);
                intent.putExtra("eventId", e.getId());
                intent.putExtra("eventTitle", e.getTitle());
                intent.putExtra("eventDescription", e.getDescription());
                intent.putExtra("eventAddressStreet", e.getAddressStreet());
                intent.putExtra("eventAddressCity", e.getAddressCity());
                intent.putExtra("eventAddressState", e.getAddressState());
                intent.putExtra("eventAddressNeighborhood", e.getAddressNeighborhood());
                intent.putExtra("eventAddressZip", e.getAddressZip());
                intent.putExtra("eventSkill1", e.getSkill1());
                intent.putExtra("eventSkill2", e.getSkill2());
                intent.putExtra("eventSkill3", e.getSkill3());
                intent.putExtra("eventUrl", e.getDisplayUrl());
                intent.putExtra("eventDate", Utils.getDateString(e.getStartTime()));
                intent.putExtra("eventTime", Utils.getTimeString(e.getStartTime()) + "-" + Utils.getTimeString(e.getEndTime()));
                intent.putExtra("eventOrgName", e.getOrganization().getName());
                intent.putExtra("eventOrgId", e.getOrganization().getId());
                intent.putExtra("eventOrgAddress", e.getOrganization().getAddress());
                intent.putExtra("eventOrgPhone", e.getOrganization().getPhoneNumber().getNumber());
                intent.putExtra("eventOrgUrl", e.getOrganization().getUrl());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }


    private void setupSwipeToRefresh() {
        mSwipeContainer = (SwipeRefreshLayout) mFragmentView.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                 @Override
                                                 public void onRefresh() {
                                                     if (Network.isAvailable(EventsFragment.this.getContext())) {
                                                         populateEvents();
                                                     } else {
                                                         mSwipeContainer.setRefreshing(false);
                                                         Toast.makeText(getActivity(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
                                                     }
                                                 }
                                             }

        );
        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(R.color.colorPrimary);
    }

    protected User getUser() {
        PinchApplication pinchApplication = (PinchApplication) getActivity().getApplication();
        return pinchApplication.getUser();
    }
}
