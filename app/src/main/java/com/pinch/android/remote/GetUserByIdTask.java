package com.pinch.android.remote;

import android.os.AsyncTask;

import com.pinch.backend.userEndpoint.model.User;

import java.io.IOException;

public class GetUserByIdTask extends AsyncTask<Long, Void, User> {

    GetUserByIdResultListener listener;

    public GetUserByIdTask(GetUserByIdResultListener listener) {
        this.listener = listener;
    }

    public interface GetUserByIdResultListener {
        void onUserUpdate(User user);
    }

    @Override
    protected void onPostExecute(User user) {
        listener.onUserUpdate(user);
    }

    @Override
    protected User doInBackground(Long... params) {
        try {
            return Endpoints.getInstance().userEndpoint.get(params[0]).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
