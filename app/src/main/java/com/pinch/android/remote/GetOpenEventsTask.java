package com.pinch.android.remote;

import android.os.AsyncTask;

import com.pinch.backend.eventEndpoint.model.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetOpenEventsTask extends AsyncTask<Void, Void, List<Event>> {

    GetOpenEventsResultsListener listener;

    public GetOpenEventsTask(GetOpenEventsResultsListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Event> doInBackground(Void... params) {
        try {
            return Endpoints.getInstance().eventEndpoint.getAllFuture().execute().getItems();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<Event> events) {
        listener.onEventsFetched(events);
    }

    public interface GetOpenEventsResultsListener {
        void onEventsFetched(List<Event> events);
    }
}