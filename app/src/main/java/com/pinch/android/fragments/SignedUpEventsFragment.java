package com.pinch.android.fragments;

import com.facebook.AccessToken;
import com.pinch.android.R;
import com.pinch.android.remote.GetSignedUpEventsForUserTask;
import com.pinch.backend.eventEndpoint.model.Event;

import java.util.List;

import static com.pinch.android.util.SharedPreferenceUtil.getSharedPreferenceLongFromKey;

public class SignedUpEventsFragment extends EventsFragment implements GetSignedUpEventsForUserTask.GetSignedUpEventsForUseResultListener{
    protected Boolean checkAccessToken = true;

    // TODO - requires update because fragement is loaded up early.
    protected void populateEvents() {
        if(AccessToken.getCurrentAccessToken() != null) {
            Long userId = getSharedPreferenceLongFromKey(this.getContext(), getString(R.string.user_id));
            if(userId != null){
                new GetSignedUpEventsForUserTask(this).execute(userId);
            }
        }
    }

    @Override
    public void onEventsFetched(List<Event> events) {
        if(events != null){
            mEventsArray.addAll(events);
            mEventsAdapter.notifyDataSetChanged();
            mSwipeContainer.setRefreshing(false);
        }
    }
}
