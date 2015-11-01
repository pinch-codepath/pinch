package com.pinch.android.remote;

import android.os.AsyncTask;

import com.pinch.backend.userEndpoint.model.Organization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetAffiliationsForUserTask extends AsyncTask<Long, Void, List<Organization>> {
    GetSignedUpEventsForUserResultListener listener;

    public GetAffiliationsForUserTask(GetSignedUpEventsForUserResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Organization> doInBackground(Long... userIds) {
        try {
            List<Organization> items = Endpoints.getInstance().userEndpoint.getAffiliationsForUser(userIds[0]).execute().getItems();
            if (items == null) {
                items = new ArrayList<>();
            }
            return items;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    protected void onPostExecute(List<Organization> organizations) {
        listener.onEventsFetched(organizations);
    }

    public interface GetSignedUpEventsForUserResultListener {
        void onEventsFetched(List<Organization> organizations);
    }
}
