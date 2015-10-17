package com.pinch.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.pinch.android.activities.EventDetailsActivity;
import com.pinch.android.R;
import com.pinch.android.adapters.EventsArrayAdapter;
import com.pinch.android.remote.GetOpenEventsTask;
import com.pinch.backend.eventEndpoint.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventsFragment extends Fragment {

    protected View mFragmentView;
    protected ArrayList<Event> mEventsArray;
    protected EventsArrayAdapter mEventsAdapter;
    protected ListView mLvEvents;

    private SwipeRefreshLayout mSwipeContainer;

    public EventsFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_events, container, false);
        setupFragment();
        return mFragmentView;
    }

    protected void setupFragment() {
        setupViewObjects();
        setupSwipeToRefresh();
        populateEvents();
    }

    protected void setupViewObjects() {
        mLvEvents = (ListView) mFragmentView.findViewById(R.id.lvEvents);
        mEventsArray = new ArrayList<>();
        mEventsAdapter = new EventsArrayAdapter(getActivity(), mEventsArray);
        mLvEvents.setAdapter(mEventsAdapter);
        mLvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void populateEvents() {
        new GetOpenEventsTask(new GetOpenEventsTask.GetOpenEventsResultsListener() {
            @Override
            public void onEventsFetched(List<Event> events) {
                mEventsArray.addAll(events);
                mEventsAdapter.notifyDataSetChanged();
                mSwipeContainer.setRefreshing(false);
            }
        }).execute();
    }

    private void setupSwipeToRefresh() {
        mSwipeContainer = (SwipeRefreshLayout) mFragmentView.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isNetworkAvailable()) {
                    populateEvents();
                } else {
                    mSwipeContainer.setRefreshing(false);
                    Toast.makeText(getActivity(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
                }
            }
        });
        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

}
