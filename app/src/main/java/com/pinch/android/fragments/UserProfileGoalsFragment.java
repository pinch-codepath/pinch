package com.pinch.android.fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import com.pinch.android.R;

/**
 * Created by vjobanputra on 10/31/15.
 */
public class UserProfileGoalsFragment extends Fragment {
    protected View fragmentView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.fragment_profile_goals, container, false);
//        setupViews();
//        setupListener();
        return fragmentView;
    }

}
