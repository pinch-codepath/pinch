package com.pinch.android.remote;

import android.os.AsyncTask;

import java.io.IOException;

public class RemoveEventSignUpTask extends AsyncTask<Long, Void, Void> {

    RemoveEventSignUpTaskResultListener listener;

    public RemoveEventSignUpTask(RemoveEventSignUpTaskResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Void v) {
        listener.onRemoveEventSignUp();
    }

    @Override
    protected Void doInBackground(Long... params) {
        try {
            return Endpoints.getInstance().signUpEndpoint.unregister(params[0]).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface RemoveEventSignUpTaskResultListener {
        void onRemoveEventSignUp();
    }
}
