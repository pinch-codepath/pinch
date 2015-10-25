package com.pinch.android.remote;

import android.os.AsyncTask;

import com.pinch.backend.signUpEndpoint.model.SignUp;

import java.io.IOException;

public class RemoveEventSignUpTask extends AsyncTask<SignUp, Void, Void> {

    RemoveEventSignUpTaskResultListener listener;

    public RemoveEventSignUpTask(RemoveEventSignUpTaskResultListener listener) {
        this.listener = listener;
    }

    public interface RemoveEventSignUpTaskResultListener {
        void onRemoveEventSignUp();
    }

    @Override
    protected void onPostExecute(Void v) {
        listener.onRemoveEventSignUp();
    }

    @Override
    protected Void doInBackground(SignUp... params) {
        try {
            return Endpoints.getInstance().signUpEndpoint.delete(params[0]).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
