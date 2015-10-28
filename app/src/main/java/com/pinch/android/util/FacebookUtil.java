package com.pinch.android.util;

import android.support.v4.app.FragmentManager;

import com.pinch.android.dialogs.FacebookLoginDialog;

public class FacebookUtil {

    public static void openFacebookLoginDialog(FragmentManager fm) {
        FacebookLoginDialog facebookLoginDialog = FacebookLoginDialog.newInstance();
        facebookLoginDialog.show(fm, "dialog_login_facebook");
    }
}
