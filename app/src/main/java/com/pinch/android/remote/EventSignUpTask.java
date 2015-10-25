package com.pinch.android.remote;

import android.os.AsyncTask;

import com.pinch.backend.signUpEndpoint.model.SignUp;

import java.io.IOException;

public class EventSignUpTask extends AsyncTask<SignUp, Void, SignUp> {

    EventSignUpTaskResultListener listener;

    public EventSignUpTask(EventSignUpTaskResultListener listener) {
        this.listener = listener;
    }

    public interface EventSignUpTaskResultListener {
        void onEventSignUp(SignUp signUp);
    }

    @Override
    protected void onPostExecute(SignUp signUp) {
        listener.onEventSignUp(signUp);
    }

    @Override
    protected SignUp doInBackground(SignUp... params) {
        try {
            return Endpoints.getInstance().signUpEndpoint.insert(params[0]).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return params[0];
        }
    }
}
