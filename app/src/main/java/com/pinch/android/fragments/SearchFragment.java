package com.pinch.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pinch.android.R;
import com.pinch.android.SearchFilters;
import com.pinch.android.activities.EventDetailsActivity;
import com.pinch.android.activities.SearchFiltersActivity;
import com.pinch.android.adapters.EventsImageArrayAdapter;
import com.pinch.android.remote.GetFilteredEventsTask;
import com.pinch.backend.eventEndpoint.model.Event;
import com.pinch.backend.eventEndpoint.model.GeoPt;
import com.pinch.backend.eventEndpoint.model.Search;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private View mFragmentView;
    protected ArrayList<Event> mEventsArray;
    protected EventsImageArrayAdapter mEventsAdapter;
    protected ListView mLvEvents;

    private static final int REQUEST_CODE = 194;
    private static final int RESULT_OK = 200;
    private SearchFilters searchFilters;

    private SwipeRefreshLayout mSwipeContainer;

    public SearchFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_search_filters, container, false);
        setupViewObjects();
        setupSwipeToRefresh();
        populateEvents();
        return mFragmentView;
    }

    protected void setupViewObjects() {
        searchFilters = new SearchFilters();

        SearchView searchBar = (SearchView) mFragmentView.findViewById(R.id.svEvents);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(getActivity(), SearchFiltersActivity.class);
                searchIntent.putExtra("filters", searchFilters);
                startActivityForResult(searchIntent, REQUEST_CODE);
            }
        });

        mLvEvents = (ListView) mFragmentView.findViewById(R.id.lvEvents);
        mEventsArray = new ArrayList<>();
        mEventsAdapter = new EventsImageArrayAdapter(getActivity(), mEventsArray);
        mLvEvents.setAdapter(mEventsAdapter);
        mLvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                startActivity(intent);
            }
        });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            searchFilters = data.getParcelableExtra("filters");
            //populate list with the search filters
            populateEvents();
            Toast.makeText(getActivity(), "UPDATING RESULTS", Toast.LENGTH_LONG).show();
        }
    }

    private void populateEvents() {
        Search search = new Search();

        search.setText(searchFilters.getKeyword());
        search.setCurrentLocation(getLocation());
        search.setDistanceInMeters((int) (searchFilters.getDistance() * 1609.34));
        if(searchFilters.getKeyword().equals("")) {
            search.setText(null);
        }
        search.setStartTime(searchFilters.getFromDateTime());
        search.setEndTime(searchFilters.getToDateTime());

        new GetFilteredEventsTask(new GetFilteredEventsTask.GetFilteredEventsResultsListener() {
            @Override
            public void onEventsFetched(List<Event> events) {
                mEventsArray.clear();
                if(events != null && events.size() > 0) {
                    mEventsArray.addAll(events);
                }
                mEventsAdapter.notifyDataSetChanged();
                mSwipeContainer.setRefreshing(false);
            }
        }).execute(search);
    }

    public Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private GeoPt getLocation() {
        GeoPt geoPt = new GeoPt();

        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;     // Or use LocationManager.GPS_PROVIDER

        Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);

        if(lastKnownLocation != null) {
            geoPt.setLatitude((float) lastKnownLocation.getLatitude());
            geoPt.setLongitude((float) lastKnownLocation.getLongitude());
            Toast.makeText(getContext(), "Location: " + geoPt.getLatitude() + ", " + geoPt.getLongitude(), Toast.LENGTH_LONG).show();
        }
        return geoPt;
    }
}
