package com.pinch.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinch.android.R;
import com.pinch.android.Utils;
import com.pinch.backend.eventEndpoint.model.Event;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventsImageArrayAdapter extends ArrayAdapter<Event> {

    public EventsImageArrayAdapter(Context context, List<Event> objects) {
        super(context, 0, objects);
    }

    private static class ViewHolder {
        ImageView ivEventImage;
        TextView tvEventTitle;
        TextView tvEventDate;
        TextView tvEventTime;
        ImageView ivFav;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event event = getItem(position);

        final ViewHolder viewHolder;

        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event_image, parent, false);
            viewHolder.ivEventImage = (ImageView) convertView.findViewById(R.id.ivEventImage);
            viewHolder.tvEventTitle = (TextView) convertView.findViewById(R.id.tvEventTitle);
            viewHolder.tvEventDate = (TextView) convertView.findViewById(R.id.tvEventDate);
            viewHolder.ivFav = (ImageView) convertView.findViewById(R.id.ivFav);
            viewHolder.tvEventTime = (TextView) convertView.findViewById(R.id.tvEventTime);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(getContext()).load(event.getOrganization().getDisplayUrl()).resize(getContext().getResources().getDisplayMetrics().widthPixels, 0).into(viewHolder.ivEventImage);

        viewHolder.tvEventTitle.setText(event.getTitle());
        viewHolder.tvEventDate.setText(Utils.getDateString(event.getStartTime()));
        viewHolder.tvEventTime.setText(Utils.getTimeString(event.getStartTime()) + "-" + Utils.getTimeString(event.getEndTime()));

//        if(event.getOrganization().isFavorite()) {
//            viewHolder.ivFav.setImageDrawable(getContext().getDrawable(R.drawable.ic_action_fav_red));
//        }
//        else {
//            viewHolder.ivFav.setImageDrawable(getContext().getDrawable(R.drawable.ic_action_fav_gray));
//        }

        return convertView;
    }

}
