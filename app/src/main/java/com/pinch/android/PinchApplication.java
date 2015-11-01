package com.pinch.android;

import android.content.Context;

import com.facebook.AccessToken;
import com.pinch.android.events.AddOrgTabsEvent;
import com.pinch.android.events.AddUserTabsEvent;
import com.pinch.android.events.RefreshUserFavoritesEvent;
import com.pinch.android.events.RefreshUserSignupsEvent;
import com.pinch.android.events.RemoveUserTabsEvent;
import com.pinch.android.remote.GetUserByAuthTask;
import com.pinch.android.remote.UpdateGSMTokenTask;
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
public class PinchApplication extends android.app.Application implements GetUserByAuthTask.GetUserByAuthResultListener, UpdateGSMTokenTask.UpdateGSMTokenUserResultListener {
    private static Context context;
    public Bus bus = new Bus();
    private User user;
    private String gcmToken;

    @Override
    public void onCreate() {
        super.onCreate();
        PinchApplication.context = this;
    }

    public User getUser() {
        if (user == null && AccessToken.getCurrentAccessToken() != null) {
            new GetUserByAuthTask(this).execute(AccessToken.getCurrentAccessToken().getUserId());
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user == null) {
            bus.post(new RemoveUserTabsEvent());
        } else {
            bus.post(new AddUserTabsEvent());
            bus.post(new RefreshUserFavoritesEvent());
            bus.post(new RefreshUserSignupsEvent());
            if (user.getAffiliations() != null && user.getAffiliations().size() > 0) {
                bus.post(new AddOrgTabsEvent());
            }
            if(gcmToken != null && user.getGcmToken() != gcmToken){
                // update gcm token
                UpdateGSMTokenTask.UpdateGSMTokenRequest request = new UpdateGSMTokenTask.UpdateGSMTokenRequest(user.getId(), gcmToken);
                new UpdateGSMTokenTask(this).execute(request);
            }
        }
    }

    @Override
    public void onUserUpdate(User user) {
        setUser(user);
    }

    public String getGcmToken() {
        return gcmToken;
    }

    public void setGcmToken(String gcmToken) {
        this.gcmToken = gcmToken;
        if(user != null && user.getGcmToken() != gcmToken){
            UpdateGSMTokenTask.UpdateGSMTokenRequest request = new UpdateGSMTokenTask.UpdateGSMTokenRequest(user.getId(), gcmToken);
            new UpdateGSMTokenTask(this).execute(request);
            // update gcm token
        }
    }

    @Override
    public void onGSMTokenUpdate(User user) {
        this.user = user;
    }
}