package com.pinch.backend;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

import com.pinch.backend.model.User;

import java.io.IOException;

public class PushService {
    private static PushService ourInstance = new PushService();
    public static final String API_KEY = "AIzaSyDvpCJPQibFctETieSHmaiNhVqQ8zV6vQI";

    public static PushService getInstance() {
        return ourInstance;
    }

    private PushService() {
    }

    public void sendPush(User user, String title, String message) {
        String gcmToken = user.getGcmToken();
        if(gcmToken != null) {
            Sender sender = new Sender(API_KEY);
            Message msg = new Message.Builder().addData("message", message).addData("title", title).build();
            try {
                Result result = sender.send(msg, gcmToken, 5);
                if (result.getMessageId() != null) {
                    String canonicalRegId = result.getCanonicalRegistrationId();
                    if (canonicalRegId != null) {
                        user.setGcmToken(canonicalRegId);
                        OfyService.ofy().save().entity(user).now();
                    }
                } else {
                    String error = result.getErrorCodeName();
                    if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                        user.setGcmToken(null);
                        OfyService.ofy().save().entity(user).now();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

