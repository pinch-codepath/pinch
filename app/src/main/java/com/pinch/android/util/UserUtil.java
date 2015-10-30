package com.pinch.android.util;

import com.pinch.android.remote.InsertIfMissingUserTask;
import com.pinch.backend.userEndpoint.model.User;

import org.json.JSONObject;

public class UserUtil {
    public final static String FACEBOOK_AUTH_SOURCE = "facebook";

    public final static void getUserFromGraphResponse(JSONObject jsonObject, InsertIfMissingUserTask.InsertIfMissingUserResultListener listener) {
        String id = jsonObject.optString("id");
        String name = jsonObject.optString("name");
        User user = new User();
        user.setAuthId(id);
        user.setAuthSource(UserUtil.FACEBOOK_AUTH_SOURCE);
        user.setName(name);
        new InsertIfMissingUserTask(listener).execute(user);
    }
}
