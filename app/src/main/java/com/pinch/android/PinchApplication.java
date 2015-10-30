package com.pinch.android;

import android.content.Context;

import com.facebook.AccessToken;
import com.pinch.android.events.RefreshUserFavoritesEvent;
import com.pinch.android.events.RefreshUserSignupsEvent;
import com.pinch.android.remote.GetUserByAuthTask;
import com.pinch.backend.userEndpoint.model.User;
import com.squareup.otto.Bus;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     TwitterClient client = TwitterApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class PinchApplication extends android.app.Application implements GetUserByAuthTask.GetUserByAuthResultListener {
    private static Context context;

    private User user;

    public Bus bus = new Bus();

    @Override
    public void onCreate() {
        super.onCreate();
        PinchApplication.context = this;
    }

    public User getUser() {
        if(user == null && AccessToken.getCurrentAccessToken() != null) {
            new GetUserByAuthTask(this).execute(AccessToken.getCurrentAccessToken().getUserId());
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        bus.post(new RefreshUserFavoritesEvent());
        bus.post(new RefreshUserSignupsEvent());
    }

    @Override
    public void onUserUpdate(User user) {
        setUser(user);
    }
}