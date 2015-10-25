package com.pinch.android.remote;

import android.os.AsyncTask;

import com.pinch.backend.eventEndpoint.model.Event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetSignedUpEventsForUserTask extends AsyncTask<Long, Void, List<Event>> {
    GetSignedUpEventsForUseResultListener listener;

    public interface GetSignedUpEventsForUseResultListener {
        void onEventsFetched(List<Event> events);
    }

    public GetSignedUpEventsForUserTask(GetSignedUpEventsForUseResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Event> doInBackground(Long... userIds) {
        try {
            return Endpoints.getInstance().eventEndpoint.getSignedUpEventsForUser(userIds[0]).execute().getItems();
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
