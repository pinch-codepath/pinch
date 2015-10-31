package com.pinch.android.remote;

import android.os.AsyncTask;

import com.pinch.backend.favoriteEndpoint.model.Favorite;

import java.io.IOException;

public class HasFavoriteForOrgTask extends AsyncTask<Favorite, Void, Favorite> {

    HasFavoriteForOrgResultListener listener;

    public HasFavoriteForOrgTask(HasFavoriteForOrgResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Favorite favorite) {
        listener.onHasFavoriteResult(favorite);
    }

    @Override
    protected Favorite doInBackground(Favorite... params) {
        try {
            Favorite favorite = params[0];
            return Endpoints.getInstance().favoriteEndpoint.query(favorite).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface HasFavoriteForOrgResultListener {
        void onHasFavoriteResult(Favorite favorite);
    }
}
