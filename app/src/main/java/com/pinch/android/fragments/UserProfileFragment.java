package com.pinch.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.pinch.android.R;
import com.squareup.picasso.Picasso;

import static com.pinch.android.util.FacebookUtil.openFacebookLoginDialog;
import static com.pinch.android.util.SharedPreferenceUtil.getSharedPreferenceStringFromKey;


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
            openFacebookLoginDialog(getActivity().getSupportFragmentManager());
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
                            + getSharedPreferenceStringFromKey(getContext(), getString(R.string.auth_source_id))
                            + "/picture?width=" +
                            String.valueOf(getContext().getResources().getDisplayMetrics().widthPixels))
                    .into(ivProfilePic);
        }
        tvProfileName.setText(getSharedPreferenceStringFromKey(getContext(), getString(R.string.user_name)));
        tvProfileLocation.setText(getSharedPreferenceStringFromKey(getContext(), getString(R.string.user_location)));
        tvProfileBio.setText(getSharedPreferenceStringFromKey(getContext(), getString(R.string.user_bio)));
    }

}