package com.pinch.android.remote;

import android.os.AsyncTask;

import com.pinch.backend.favoriteEndpoint.model.Favorite;

import java.io.IOException;

public class FavoriteOrgTask extends AsyncTask<Favorite, Void, Favorite> {

    FavoriteOrgResultListener listener;

    public FavoriteOrgTask(FavoriteOrgResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Favorite v) {
        listener.onFavoriteOrg(v);
    }

    @Override
    protected Favorite doInBackground(Favorite... params) {
        try {
            return Endpoints.getInstance().favoriteEndpoint.register(params[0]).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface FavoriteOrgResultListener {
        void onFavoriteOrg(Favorite v);
    }
}
