package com.pinch.android.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.pinch.android.R;

import org.json.JSONObject;

public class FacebookLoginDialog extends DialogFragment{

    CallbackManager callbackManager;

    public FacebookLoginDialog() {}

    public interface FacebookLoginDialogListener {
        void onLoginSuccess();
    }

    public static FacebookLoginDialog newInstance() {
        return new FacebookLoginDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_login_facebook, container);

        callbackManager = CallbackManager.Factory.create();

        // Other app specific specialization
        // loginButton.setReadPermissions("user_friends");

        // Callback registration
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.btLogin);
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequestAsyncTask request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        writeToSharedPreferences(getString(R.string.user_id), jsonObject.optString("id"));
                        writeToSharedPreferences(getString(R.string.user_name), jsonObject.optString("name"));
                        writeToSharedPreferences(getString(R.string.user_bio), jsonObject.optString("bio"));
                        writeToSharedPreferences(getString(R.string.user_location), jsonObject.optString("location"));

                        if (AccessToken.getCurrentAccessToken() != null) {
                            FacebookLoginDialogListener listener = (FacebookLoginDialogListener) getActivity();
                            listener.onLoginSuccess();
                            dismiss();
                        }
                    }
                }).executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        ImageView cancelButton = (ImageView) view.findViewById(R.id.btCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void writeToSharedPreferences(String key, String value) {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

}
