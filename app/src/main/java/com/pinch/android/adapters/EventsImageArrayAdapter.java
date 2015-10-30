package com.pinch.android.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinch.android.R;
import com.pinch.android.Utils;
import com.pinch.android.activities.EventDetailsActivity;
import com.pinch.backend.eventEndpoint.model.Event;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventsImageArrayAdapter extends RecyclerView.Adapter<EventsImageArrayAdapter.ViewHolder> {

    private Context mContext;
    private List<Event> mEvents;

    public EventsImageArrayAdapter(List<Event> events) {
        mEvents = events;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView ivEventImage;
        TextView tvEventTitle;
        TextView tvEventDate;
        TextView tvEventTime;
        ImageView ivFav;

        public ViewHolder(View itemView) {
            super(itemView);

            ivEventImage = (ImageView) itemView.findViewById(R.id.ivEventImage);
            tvEventTitle = (TextView) itemView.findViewById(R.id.tvEventTitle);
            tvEventDate = (TextView) itemView.findViewById(R.id.tvEventDate);
            ivFav = (ImageView) itemView.findViewById(R.id.ivFav);
            tvEventTime = (TextView) itemView.findViewById(R.id.tvEventTime);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Event e = mEvents.get(position);

            Intent intent = new Intent(mContext, EventDetailsActivity.class);
            intent.putExtra("eventId", e.getId());
            intent.putExtra("eventTitle", e.getTitle());
            intent.putExtra("eventDescription", e.getDescription());
            intent.putExtra("eventAddressStreet", e.getAddressStreet());
            intent.putExtra("eventAddressCity", e.getAddressCity());
            intent.putExtra("eventAddressState", e.getAddressState());
            intent.putExtra("eventAddressNeighborhood", e.getAddressNeighborhood());
            intent.putExtra("eventAddressZip", e.getAddressZip());
            intent.putExtra("eventSkill1", e.getSkill1());
            intent.putExtra("eventSkill2", e.getSkill2());
            intent.putExtra("eventSkill3", e.getSkill3());
            intent.putExtra("eventUrl", e.getDisplayUrl());
            intent.putExtra("eventDate", Utils.getDateString(e.getStartTime()));
            intent.putExtra("eventTime", Utils.getTimeString(e.getStartTime()) + "-" + Utils.getTimeString(e.getEndTime()));
            intent.putExtra("eventOrgName", e.getOrganization().getName());
            intent.putExtra("eventOrgId", e.getOrganization().getId());
            intent.putExtra("eventOrgAddress", e.getOrganization().getAddress());
            intent.putExtra("eventOrgPhone", e.getOrganization().getPhoneNumber().getNumber());
            intent.putExtra("eventOrgUrl", e.getOrganization().getUrl());
            mContext.startActivity(intent);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View eventView = inflater.inflate(R.layout.item_event_image, parent, false);

        // Return a new holder instance
        return new ViewHolder(eventView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Event event = mEvents.get(position);

        Picasso.with(mContext).load(event.getDisplayUrl()).resize(mContext.getResources().getDisplayMetrics().widthPixels, 0).into(viewHolder.ivEventImage);

        viewHolder.tvEventTitle.setText(event.getTitle());
        viewHolder.tvEventDate.setText(Utils.getDateString(event.getStartTime()));
        viewHolder.tvEventTime.setText(Utils.getTimeString(event.getStartTime()) + "-" + Utils.getTimeString(event.getEndTime()));
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }
}
