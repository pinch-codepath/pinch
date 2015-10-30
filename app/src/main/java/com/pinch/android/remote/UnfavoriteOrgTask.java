package com.pinch.android.remote;

import android.os.AsyncTask;

import java.io.IOException;

public class UnfavoriteOrgTask extends AsyncTask<Long, Void, Void> {

    UnfavoriteOrgResultListener listener;

    public UnfavoriteOrgTask(UnfavoriteOrgResultListener listener) {
        this.listener = listener;
    }

    public interface UnfavoriteOrgResultListener {
        void onUnfavoriteOrg();
    }

    @Override
    protected void onPostExecute(Void v) {
        listener.onUnfavoriteOrg();
    }

    @Override
    protected Void doInBackground(Long... params) {
        try {
            return Endpoints.getInstance().favoriteEndpoint.unregister(params[0]).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
