package com.pinch.android.fragments;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.pinch.android.PinchApplication;
import com.pinch.android.R;
import com.pinch.android.activities.LoginActivity;
import com.pinch.android.adapters.EventsFragmentPagerAdapter;
import com.pinch.android.adapters.UserProfilePagerAdapter;
import com.pinch.android.util.UserUtil;
import com.pinch.backend.userEndpoint.model.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.pinch.android.util.FacebookUtil.openFacebookLoginDialog;


public class UserProfileFragment extends Fragment {

    private View mFragmentView;

    ImageView ivCoverPic;
    ImageView ivProfilePic;
    Bitmap src;

    private ViewPager mVPager;
    private UserProfilePagerAdapter mUserProfilePagerAdapter;
    private PagerSlidingTabStrip mTabStrip;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_profile, container, false);

        if (AccessToken.getCurrentAccessToken() != null) {
            PinchApplication pinchApplication = (PinchApplication) getActivity().getApplication();
            User user = pinchApplication.getUser();
            if (user != null) {
                setupViewObjects(user);
            }
        } else {
            openFacebookLoginDialog(getActivity().getSupportFragmentManager());
        }

        return mFragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mVPager = (ViewPager) mFragmentView.findViewById(R.id.viewpager2);
        mUserProfilePagerAdapter = new UserProfilePagerAdapter(getChildFragmentManager());
        mVPager.setAdapter(mUserProfilePagerAdapter);
        mTabStrip = (PagerSlidingTabStrip) mFragmentView.findViewById(R.id.tabs2);
        mTabStrip.setViewPager(mVPager);
        mUserProfilePagerAdapter.notifyDataSetChanged();
    }

    private void setupViewObjects(User user) {
        ivCoverPic = (ImageView) mFragmentView.findViewById(R.id.ivCoverPic);
        ivProfilePic = (ImageView) mFragmentView.findViewById(R.id.ivProfilePic);
        TextView tvProfileName = (TextView) mFragmentView.findViewById(R.id.tvProfileName);
        TextView tvProfileLocation = (TextView) mFragmentView.findViewById(R.id.tvProfileLocation);

        Picasso.with(getContext())
                .load("https://graph.facebook.com/"
                        + user.getAuthId()
                        + "/picture?width=" +
                        String.valueOf(getContext().getResources().getDisplayMetrics().widthPixels))
                .into(ivProfilePic /*new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        src = bitmap;
                        Resources res = getResources();
                        RoundedBitmapDrawable dr =
                                RoundedBitmapDrawableFactory.create(res, src);
                        dr.setCornerRadius(500f);
                        ivProfilePic.setImageDrawable(dr);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                }*/);


        GraphRequest request =
                GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject ret, GraphResponse graphResponse) {
                        JSONObject cover = null;
                        String url = null;
                        try {
                            cover = ret.getJSONObject("cover");
                            url = cover.getString("source");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Picasso.with(getContext()).load(url).into(ivCoverPic);
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "cover");
        request.setParameters(parameters);
        request.executeAsync();

        tvProfileName.setText(user.getName());
    }

    private void setupViewPager() {
//        mVPager = (ViewPager) mFragmentView.findViewById(R.id.viewpager2);
//        mUserProfilePagerAdapter = new UserProfilePagerAdapter(getChildFragmentManager(), getContext());
//        mVPager.setAdapter(mUserProfilePagerAdapter);
//        mTabStrip = (PagerSlidingTabStrip) mFragmentView.findViewById(R.id.tabs2);
//        mTabStrip.setViewPager(mVPager);
//
//        ViewPager vpPager = (ViewPager) mFragmentView.findViewById(R.id.vpPager);
//        FragmentPagerAdapter adapterViewPager = new UserProfilePagerAdapter(getChildFragmentManager());
//        vpPager.setAdapter(adapterViewPager);
    }

}