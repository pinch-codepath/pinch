package com.pinch.android.fragments;

import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pinch.android.R;
import com.pinch.android.SearchFilters;
import com.pinch.android.activities.SearchFiltersActivity;
import com.pinch.android.adapters.EventsImageArrayAdapter;
import com.pinch.android.remote.GetFilteredEventsTask;
import com.pinch.android.util.Network;
import com.pinch.backend.eventEndpoint.model.Event;
import com.pinch.backend.eventEndpoint.model.GeoPt;
import com.pinch.backend.eventEndpoint.model.Search;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private static final int REQUEST_CODE = 194;
    private static final int RESULT_OK = 200;
    protected ArrayList<Event> mEventsArray;
    protected EventsImageArrayAdapter mEventsAdapter;
    protected RecyclerView mRvEvents;
    private View mFragmentView;
    private FloatingActionButton fabSearch;
    private SearchFilters searchFilters;

    private boolean inSearchMode = false;

    private SwipeRefreshLayout mSwipeContainer;

    public SearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        setupViewObjects();
        setupSwipeToRefresh();
        populateEvents();
        return mFragmentView;
    }

    protected void setupViewObjects() {
        searchFilters = new SearchFilters();

        fabSearch = (FloatingActionButton) mFragmentView.findViewById(R.id.fabSearch);
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(!inSearchMode){
                    Intent searchIntent = new Intent(getActivity(), SearchFiltersActivity.class);
                    searchIntent.putExtra("filters", searchFilters);
                    ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(SearchFragment.this.getActivity(), fabSearch, "search");
                    getActivity().startActivityForResult(searchIntent, REQUEST_CODE, options.toBundle());
                    fabSearch.setImageResource(R.drawable.ic_action_name);
                    inSearchMode = true;
                } else {
                    fabSearch.setImageResource(R.drawable.ic_tab_search);
                    inSearchMode = false;
                    searchFilters = new SearchFilters();
                    populateEvents();
                    Toast.makeText(getActivity(), "Cleared search results.", Toast.LENGTH_LONG).show();
                }
            }
        });

        mRvEvents = (RecyclerView) mFragmentView.findViewById(R.id.rvEvents);
        mEventsArray = new ArrayList<>();
        mEventsAdapter = new EventsImageArrayAdapter(mEventsArray);
        mRvEvents.setAdapter(mEventsAdapter);
        mRvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupSwipeToRefresh() {
        mSwipeContainer = (SwipeRefreshLayout) mFragmentView.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Network.isAvailable(SearchFragment.this.getContext())) {
                    populateEvents();
                } else {
                    mSwipeContainer.setRefreshing(false);
                    Toast.makeText(getActivity(), "Please check your internet connection!", Toast.LENGTH_LONG).show();
                }
            }
        });
        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(R.color.colorPrimary);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            searchFilters = data.getParcelableExtra("filters");
            //populate list with the search filters
            populateEvents();
            Toast.makeText(getActivity(), "UPDATING RESULTS", Toast.LENGTH_LONG).show();
        } else {
            fabSearch.setImageResource(R.drawable.ic_tab_search);
            inSearchMode = false;
        }
    }

    private void populateEvents() {
        Search search = new Search();

        search.setText(searchFilters.getKeyword());
        if (searchFilters.getDistance() > 0) {
            search.setCurrentLocation(getLocation());
            search.setDistanceInMeters((int) (searchFilters.getDistance() * 1609.34));
        }
        search.setStartTime(searchFilters.getFromDateTime());
        search.setEndTime(searchFilters.getToDateTime());

        new GetFilteredEventsTask(new GetFilteredEventsTask.GetFilteredEventsResultsListener() {
            @Override
            public void onEventsFetched(List<Event> events) {
                mEventsArray.clear();
                if (events != null && events.size() > 0) {
                    mEventsArray.addAll(events);
                }
                mEventsAdapter.notifyDataSetChanged();
                mSwipeContainer.setRefreshing(false);
            }
        }).execute(search);
    }

    private GeoPt getLocation() {
        GeoPt geoPt = new GeoPt();

        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;     // Or use LocationManager.GPS_PROVIDER

        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        if (lastKnownLocation != null) {
            geoPt.setLatitude((float) lastKnownLocation.getLatitude());
            geoPt.setLongitude((float) lastKnownLocation.getLongitude());
            Toast.makeText(getContext(), "Location: " + geoPt.getLatitude() + ", " + geoPt.getLongitude(), Toast.LENGTH_LONG).show();
        }
        return geoPt;
    }
}
