package com.pinch.android.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pinch.android.R;
import com.pinch.backend.eventBeanApi.model.Event;


import java.util.List;

public class EventsArrayAdapter extends ArrayAdapter<String> {

    public enum RowType {
        DATE,
        EVENT
    }

    public EventsArrayAdapter(Context context, List<String> events) {
        super(context, R.layout.item_event, events);
    }

    @Override
    public int getItemViewType(int position) {

        if (position % 6 == 0) {
            return RowType.DATE.ordinal();
        } else {
            return RowType.EVENT.ordinal();
        }

//        boolean displayDay = false;
//        Event current = getItem(position);
//
//        if (position == 0) {
//            displayDay = true;
//        } else {
//            Event previous = getItem(position - 1);
//            if (current.getStartTime() != previous.getStartTime()) {
//                displayDay = true;
//            }
//        }
//        if (displayDay) {
//            this.insert(current, position);
//            return RowType.DAY_ITEM.ordinal();
//        }
//        return RowType.EVENT_ITEM.ordinal();
    }

    @Override
    public int getViewTypeCount() {
        return RowType.values().length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);
        if (type == RowType.DATE.ordinal()) {
            return getDateView(position, convertView, parent);
        } else if (type == RowType.EVENT.ordinal()) {
            return getEventView(position, convertView, parent);
        } else {
            return convertView;
        }
    }

    private View getEventView(int position, View convertView, ViewGroup parent) {
        EventViewHolder eventViewHolder;
        if (convertView == null) {
            eventViewHolder = new EventViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event, parent, false);
            eventViewHolder.tvHours = (TextView)convertView.findViewById(R.id.tvHours);
            eventViewHolder.tvTimeTo = (TextView)convertView.findViewById(R.id.tvTimeTo);
            eventViewHolder.tvTimeFrom = (TextView)convertView.findViewById(R.id.tvTimeFrom);
            eventViewHolder.tvLocation = (TextView)convertView.findViewById(R.id.tvLocation);
            eventViewHolder.tvEventName = (TextView)convertView.findViewById(R.id.tvEventName);
            eventViewHolder.tvNonProfit = (TextView)convertView.findViewById(R.id.tvNonProfit);
            convertView.setTag(eventViewHolder);
        } else {
            eventViewHolder = (EventViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private View getDateView(int position, View convertView, ViewGroup parent) {
        DateViewHolder dateViewHolder;
        if (convertView == null) {
            dateViewHolder = new DateViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_day, parent, false);
            dateViewHolder.tvDate = (TextView)convertView.findViewById(R.id.tvDate);
            convertView.setTag(dateViewHolder);
        } else {
            dateViewHolder = (DateViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private static class EventViewHolder {
        TextView tvHours;
        TextView tvTimeTo;
        TextView tvTimeFrom;
        TextView tvLocation;
        TextView tvEventName;
        TextView tvNonProfit;
    }

    private static class DateViewHolder {
        TextView tvDate;
    }
}
