package com.pinch.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pinch.android.R;
import com.pinch.android.activities.SearchFiltersActivity;

public class SearchFiltersFragment extends EventsFragment {

    public SearchFiltersFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_search_filters, container, false);
        setupFragment();
        return mFragmentView;
    }

    @Override
    protected void setupViewObjects() {
        super.setupViewObjects();

        SearchView searchBar = (SearchView) mFragmentView.findViewById(R.id.svEvents);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(getActivity(), SearchFiltersActivity.class);
                startActivity(searchIntent);
            }
        });
    }

}
