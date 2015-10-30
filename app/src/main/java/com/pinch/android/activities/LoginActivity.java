package com.pinch.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.pinch.android.R;
import com.pinch.android.remote.InsertIfMissingUserTask;
import com.pinch.android.util.UserUtil;
import com.pinch.backend.userEndpoint.model.User;

import org.json.JSONObject;

import java.util.Arrays;

import static com.pinch.android.util.SharedPreferenceUtil.writeToSharedPreferences;

public class LoginActivity extends AppCompatActivity implements InsertIfMissingUserTask.InsertIfMissingUserResultListener {

    //key ga0RGNYHvNM5d0SLGQfpQWAPGJ8=
    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
        // Other app specific specialization
        // loginButton.setReadPermissions("user_friends");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequestAsyncTask request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        UserUtil.getUserFromGraphResponse(jsonObject, LoginActivity.this);
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

        if(AccessToken.getCurrentAccessToken() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(AccessToken.getCurrentAccessToken() != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onUserUpdate(User user) {
        writeToSharedPreferences(this, getString(R.string.user_id), user.getId());
        writeToSharedPreferences(this, getString(R.string.auth_source), user.getAuthSource());
        writeToSharedPreferences(this, getString(R.string.auth_source_id), user.getId());
        writeToSharedPreferences(this, getString(R.string.user_name), user.getName());
    }
}
