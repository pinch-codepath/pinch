package com.pinch.android.remote;

import android.os.AsyncTask;

import com.pinch.android.util.UserUtil;
import com.pinch.backend.userEndpoint.model.User;

import java.io.IOException;

public class GetUserByAuthTask extends AsyncTask<String, Void, User> {

    GetUserByAuthResultListener listener;

    public GetUserByAuthTask(GetUserByAuthResultListener listener) {
        this.listener = listener;
    }

    public interface GetUserByAuthResultListener {
        void onUserUpdate(User user);
    }

    @Override
    protected void onPostExecute(User user) {
        listener.onUserUpdate(user);
    }

    @Override
    protected User doInBackground(String... params) {
        try {
            return Endpoints.getInstance().userEndpoint.getByAuthSource(params[0], UserUtil.FACEBOOK_AUTH_SOURCE).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
