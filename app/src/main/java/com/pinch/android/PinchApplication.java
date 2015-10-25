package com.pinch.android;

import android.content.Context;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     TwitterClient client = TwitterApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class PinchApplication extends android.app.Application {
    public static final String REST_CONSUMER_KEY = "ZFefXblf2YiFj8ov6SY9iAxau";
    public static final String REST_CONSUMER_SECRET = "50d8b1S8azjUHB8ysk8JvMjfgyQzv21cmGbG8Rv2JrOPFvJJrW";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        PinchApplication.context = this;
        TwitterAuthConfig authConfig = new TwitterAuthConfig(REST_CONSUMER_KEY, REST_CONSUMER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig));
    }
}