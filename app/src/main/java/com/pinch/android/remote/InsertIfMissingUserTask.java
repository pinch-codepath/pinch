package com.pinch.android.remote;

import android.os.AsyncTask;

import com.pinch.backend.userEndpoint.model.User;

import java.io.IOException;

public class InsertIfMissingUserTask extends AsyncTask<User, Void, User> {

    InsertIfMissingUserResultListener listener;

    public InsertIfMissingUserTask(InsertIfMissingUserResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(User user) {
        listener.onUserUpdate(user);
    }

    @Override
    protected User doInBackground(User... params) {
        try {
            return Endpoints.getInstance().userEndpoint.insertIfMissing(params[0]).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return params[0];
        }
    }

    public interface InsertIfMissingUserResultListener {
        void onUserUpdate(User user);
    }
}
