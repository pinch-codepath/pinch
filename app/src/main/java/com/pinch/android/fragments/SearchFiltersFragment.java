package com.pinch.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pinch.android.R;
import com.pinch.android.activities.SearchFiltersActivity;

public class SearchFiltersFragment extends Fragment {

    private View mFragmentView;
    private static final int REQUEST_CODE = 194;
    private static final int RESULT_OK = 200;

    public SearchFiltersFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentView = inflater.inflate(R.layout.fragment_search_filters, container, false);
        setupViewObjects();
        return mFragmentView;
    }

    protected void setupViewObjects() {
        SearchView searchBar = (SearchView) mFragmentView.findViewById(R.id.svEvents);
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(getActivity(), SearchFiltersActivity.class);
                startActivityForResult(searchIntent, REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            //populate list
            Toast.makeText(getActivity(), "FILTERED RESULTS", Toast.LENGTH_LONG).show();
        }
    }
}
