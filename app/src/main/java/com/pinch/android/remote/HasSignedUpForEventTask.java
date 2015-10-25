package com.pinch.android.remote;

import android.os.AsyncTask;

import com.pinch.backend.signUpEndpoint.model.SignUp;

import java.io.IOException;

public class HasSignedUpForEventTask extends AsyncTask<SignUp, Void, Boolean> {

    HasSignedUpForEventResultListener listener;

        public HasSignedUpForEventTask(HasSignedUpForEventResultListener listener) {
            this.listener = listener;
        }

        public interface HasSignedUpForEventResultListener {
            void onHasSignUpResult(boolean signedUp);
        }

        @Override
        protected void onPostExecute(Boolean signedUp) {
            listener.onHasSignUpResult(signedUp);
        }

        @Override
        protected Boolean doInBackground(SignUp... params) {
            try {
                SignUp signUp = params[0];
                SignUp returnedValue = Endpoints.getInstance().signUpEndpoint.isSignedUp(signUp).execute();
                if(returnedValue != null) {
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
