package com.pinch.android.fragments;

import com.pinch.android.remote.GetOpenEventsTask;
import com.pinch.backend.eventEndpoint.model.Event;

import java.util.List;

public class OpenEventsFragment extends EventsFragment{

    protected void populateEvents() {
        new GetOpenEventsTask(new GetOpenEventsTask.GetOpenEventsResultsListener() {
            @Override
            public void onEventsFetched(List<Event> events) {
                mEventsArray.addAll(events);
                mEventsAdapter.notifyDataSetChanged();
                mSwipeContainer.setRefreshing(false);
            }
        }).execute();
    }

}
