package com.pinch.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.pinch.android.R;

public class UserProfileBioFragment extends Fragment {

    private View mFragmentView;
    TextView mTvBio;
    EditText mEtBio;
    TextView mTvInterests;
    EditText mEtInterests;
    ViewSwitcher bioSwitcher;
    ViewSwitcher interestsSwitcher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_profile_bio, container, false);
        setupViews();
        return mFragmentView;
    }

    public UserProfileBioFragment() {
    }

    private void setupViews() {
        mTvBio = (TextView) mFragmentView.findViewById(R.id.tvBio);
        mEtBio = (EditText) mFragmentView.findViewById(R.id.etBio);
        mTvInterests = (TextView) mFragmentView.findViewById(R.id.tvInterests);
        mEtInterests = (EditText) mFragmentView.findViewById(R.id.etInterests);
        bioSwitcher =  (ViewSwitcher) mFragmentView.findViewById(R.id.bioSwitcher);
        interestsSwitcher =  (ViewSwitcher) mFragmentView.findViewById(R.id.interestsSwitcher);

        mTvBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtBio.setText(mTvBio.getText().toString());
                bioSwitcher.showNext();
            }
        });

        mEtBio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvBio.setText(mEtBio.getText().toString());
                bioSwitcher.showPrevious();
            }
        });

        mTvInterests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtInterests.setText(mTvInterests.getText().toString());
                interestsSwitcher.showNext();
            }
        });

        mEtInterests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvInterests.setText(mEtInterests.getText().toString());
                interestsSwitcher.showPrevious();
            }
        });
    }

//    public void TextViewClicked() {
//        ViewSwitcher switcher = (ViewSwitcher) mFragmentView.findViewById(R.id.my_switcher);
//        switcher.showNext(); //or switcher.showPrevious();
//        TextView myTV = (TextView) switcher.findViewById(R.id.clickable_text_view);
//        myTV.setText("value");
//    }
}
