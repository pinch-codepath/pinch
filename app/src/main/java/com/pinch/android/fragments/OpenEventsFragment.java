package com.pinch.android.fragments;

import com.pinch.android.Utils;
import com.pinch.android.remote.GetOpenEventsTask;
import com.pinch.backend.eventEndpoint.model.Event;

import java.util.List;

public class OpenEventsFragment extends EventsFragment {

    protected void populateEvents() {
        new GetOpenEventsTask(new GetOpenEventsTask.GetOpenEventsResultsListener() {
            @Override
            public void onEventsFetched(List<Event> events) {
                if (events != null) {
                    mEventsArray.clear();
                    mEventsArray.addAll(events);
                    mEventsAdapter.notifyDataSetChanged();
                    mSwipeContainer.setRefreshing(false);
                    Utils.setMyOrganization(mEventsArray.get(0).getOrganization());
                }
            }
        }).execute();
    }

}
