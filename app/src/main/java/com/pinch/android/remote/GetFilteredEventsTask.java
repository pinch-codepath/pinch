package com.pinch.android.remote;


import android.os.AsyncTask;

import com.pinch.backend.eventEndpoint.model.Event;
import com.pinch.backend.eventEndpoint.model.Search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetFilteredEventsTask extends AsyncTask<Search, Void, List<Event>> {

    GetFilteredEventsResultsListener listener;

    public interface GetFilteredEventsResultsListener {
        void onEventsFetched(List<Event> events);
    }

    public GetFilteredEventsTask(GetFilteredEventsResultsListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Event> doInBackground(Search... filters) {
        try {
            return Endpoints.getInstance().eventEndpoint.search(filters[0]).execute().getItems();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<Event> events) {
        listener.onEventsFetched(events);
    }
}
