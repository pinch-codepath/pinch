package com.pinch.android.fragments;

import com.pinch.android.PinchApplication;
import com.pinch.android.events.RefreshUserSignupsEvent;
import com.pinch.android.remote.GetSignedUpEventsForUserTask;
import com.pinch.backend.eventEndpoint.model.Event;
import com.pinch.backend.userEndpoint.model.User;
import com.squareup.otto.Subscribe;

import java.util.List;

public class SignedUpEventsFragment extends EventsFragment implements GetSignedUpEventsForUserTask.GetSignedUpEventsForUseResultListener {
    protected Boolean checkAccessToken = true;

    boolean registered = false;

    // TODO - requires update because fragement is loaded up early.
    protected void populateEvents() {
        if (!registered) {
            ((PinchApplication) getActivity().getApplication()).bus.register(this);
            registered = true;
        }
        refreshSignUps();
    }

    @Subscribe
    public void refreshSignUps(RefreshUserSignupsEvent event) {
        refreshSignUps();
    }

    private void refreshSignUps() {
        User user = getUser();
        if (user != null) {
            new GetSignedUpEventsForUserTask(this).execute(user.getId());
        }
    }

    @Override
    public void onEventsFetched(List<Event> events) {
        mEventsArray.clear();
        if (events != null) {
            mEventsArray.addAll(events);
            mEventsAdapter.notifyDataSetChanged();
        }
        mSwipeContainer.setRefreshing(false);
    }
}
