package com.pinch.android.fragments;

import com.pinch.android.R;
import com.pinch.android.remote.GetSignedUpEventsForUserTask;
import com.pinch.backend.eventEndpoint.model.Event;

import java.util.List;

import static com.pinch.android.util.SharedPreferenceUtil.getSharedPreferenceLongFromKey;

public class SignedUpEventsFragment extends EventsFragment implements GetSignedUpEventsForUserTask.GetSignedUpEventsForUseResultListener{
    protected void populateEvents() {
        Long userId = getSharedPreferenceLongFromKey(this.getContext(), getString(R.string.user_id));
        new GetSignedUpEventsForUserTask(this).execute(userId);
    }

    @Override
    public void onEventsFetched(List<Event> events) {
                mEventsArray.addAll(events);
                mEventsAdapter.notifyDataSetChanged();
                mSwipeContainer.setRefreshing(false);
    }
}
