package com.pinch.android.remote;

import android.os.AsyncTask;

import com.pinch.backend.userEndpoint.model.User;

import java.io.IOException;

public class UpdateGSMTokenTask extends AsyncTask<UpdateGSMTokenTask.UpdateGSMTokenRequest, Void, User> {

    UpdateGSMTokenUserResultListener listener;

    public UpdateGSMTokenTask(UpdateGSMTokenUserResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(User user) {
        listener.onGSMTokenUpdate(user);
    }

    @Override
    protected User doInBackground(UpdateGSMTokenRequest... params) {
        try {
            return Endpoints.getInstance().userEndpoint.updateGCMToekn(params[0].userId, params[0].token).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface UpdateGSMTokenUserResultListener {
        void onGSMTokenUpdate(User user);
    }

    public static class UpdateGSMTokenRequest{
        public UpdateGSMTokenRequest(long userId, String token) {
            this.userId = userId;
            this.token = token;
        }

        long userId;
        String token;
    }
}
