package com.pinch.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pinch.android.R;

import org.w3c.dom.UserDataHandler;

public class ProfileFragment extends Fragment{

    private View mFragmentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_profile, container, false);
//        setupViewObjects();

        return mFragmentView;
    }
}
