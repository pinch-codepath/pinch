package com.pinch.android.remote;

import android.os.AsyncTask;

import com.pinch.backend.eventEndpoint.model.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetFavoriteEventsForUserTask extends AsyncTask<Long, Void, List<Event>> {
    GetFavoriteEventsForUserResultListener listener;

    public GetFavoriteEventsForUserTask(GetFavoriteEventsForUserResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Event> doInBackground(Long... userIds) {
        try {
            return Endpoints.getInstance().eventEndpoint.getFavoriteEventsForUser(userIds[0]).execute().getItems();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<Event> events) {
        listener.onEventsFetched(events);
    }

    public interface GetFavoriteEventsForUserResultListener {
        void onEventsFetched(List<Event> events);
    }
}
