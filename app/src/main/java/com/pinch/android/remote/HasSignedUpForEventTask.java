package com.pinch.android.remote;

import android.os.AsyncTask;

import com.pinch.backend.signUpEndpoint.model.SignUp;

import java.io.IOException;

public class HasSignedUpForEventTask extends AsyncTask<SignUp, Void, SignUp> {

    HasSignedUpForEventResultListener listener;

    public HasSignedUpForEventTask(HasSignedUpForEventResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(SignUp signedUp) {
        listener.onHasSignUpResult(signedUp);
    }

    @Override
    protected SignUp doInBackground(SignUp... params) {
        try {
            SignUp signUp = params[0];
            return Endpoints.getInstance().signUpEndpoint.query(signUp).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface HasSignedUpForEventResultListener {
        void onHasSignUpResult(SignUp signedUp);
    }
}
