package com.pinch.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pinch.android.R;
import com.pinch.android.SearchFilters;
import com.pinch.android.Utils;
import com.pinch.android.activities.EventDetailsActivity;
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

    private View mFragmentView;
    protected ArrayList<Event> mEventsArray;
    protected EventsImageArrayAdapter mEventsAdapter;
    protected ListView mLvEvents;
    private EditText etSearch;

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

        View searchBar = View.inflate(getContext(), R.layout.header_listview_search_fragment, null);

        etSearch = (EditText) searchBar.findViewById(R.id.etSearch);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                populateEvents();
                return true;
            }
        });

        ImageView ivFilters = (ImageView) searchBar.findViewById(R.id.ivFilters);
        ivFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(getActivity(), SearchFiltersActivity.class);
                searchIntent.putExtra("filters", searchFilters);
                startActivityForResult(searchIntent, REQUEST_CODE);
            }
        });

        mLvEvents = (ListView) mFragmentView.findViewById(R.id.lvEvents);
        mLvEvents.addHeaderView(searchBar);
        mEventsArray = new ArrayList<>();
        mEventsAdapter = new EventsImageArrayAdapter(getActivity(), mEventsArray);
        mLvEvents.setAdapter(mEventsAdapter);
        mLvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                Event e = mEventsArray.get(position - 1);   //to adjust for the added header
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
                intent.putExtra("eventUrl", e.getOrganization().getDisplayUrl());
                intent.putExtra("eventDate", Utils.getDateString(e.getStartTime()));
                intent.putExtra("eventTime", Utils.getTimeString(e.getStartTime()) + "-" + Utils.getTimeString(e.getEndTime()));
                intent.putExtra("eventOrgName", e.getOrganization().getName());
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
        if(searchFilters.getDistance() > 0) {
            search.setCurrentLocation(getLocation());
            search.setDistanceInMeters((int) (searchFilters.getDistance() * 1609.34));
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
