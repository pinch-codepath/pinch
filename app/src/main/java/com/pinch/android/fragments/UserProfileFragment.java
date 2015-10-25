package com.pinch.android.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.pinch.android.R;
import com.pinch.android.dialogs.FacebookLoginDialog;
import com.squareup.picasso.Picasso;


public class UserProfileFragment extends Fragment {

    private View mFragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_profile, container, false);

        if(AccessToken.getCurrentAccessToken() != null) {
            setupViewObjects();
        }
        else {
            openFacebookLoginDialog();
        }

        return mFragmentView;
    }

    private void setupViewObjects() {
        ImageView ivProfilePic = (ImageView) mFragmentView.findViewById(R.id.ivProfilePic);
        TextView tvProfileName = (TextView) mFragmentView.findViewById(R.id.tvProfileName);
        TextView tvProfileLocation = (TextView) mFragmentView.findViewById(R.id.tvProfileLocation);
        TextView tvProfileBio = (TextView) mFragmentView.findViewById(R.id.tvProfileBio);

        if(ivProfilePic != null) {
            Picasso.with(getContext())
                    .load("https://graph.facebook.com/"
                            + getSharedPreferenceFromKey(getString(R.string.user_id))
                            + "/picture?width=" +
                            String.valueOf(getContext().getResources().getDisplayMetrics().widthPixels))
                    .into(ivProfilePic);
        }
        tvProfileName.setText(getSharedPreferenceFromKey(getString(R.string.user_name)));
        tvProfileLocation.setText(getSharedPreferenceFromKey(getString(R.string.user_location)));
        tvProfileBio.setText(getSharedPreferenceFromKey(getString(R.string.user_bio)));
    }

    private void openFacebookLoginDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FacebookLoginDialog facebookLoginDialog = FacebookLoginDialog.newInstance();
        facebookLoginDialog.show(fm, "dialog_login_facebook");
    }

    private String getSharedPreferenceFromKey(String key) {
        String value;
        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getString(R.string.shared_preference_file_key), Context.MODE_PRIVATE);
        value = sharedPref.getString(key, "");
        return value;
    }
}