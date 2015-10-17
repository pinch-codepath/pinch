package com.pinch.android.remote;

import android.content.Context;
import android.os.AsyncTask;

import com.pinch.backend.eventEndpoint.model.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class GetOpenEventsTask extends AsyncTask<Void, Void, List<Event>> {
    private Context context;

    @Override
    protected List<Event> doInBackground(Void... params) {
        try {
            return Endpoints.getInstance().eventEndpoint.getAllFuture().execute().getItems();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}