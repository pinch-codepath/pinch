package com.pinch.android.adapters;

import com.google.api.client.util.DateTime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pinch.android.R;
import com.pinch.android.Utils;
import com.pinch.backend.eventEndpoint.model.Event;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventsArrayAdapter extends ArrayAdapter<Event> {

    public EventsArrayAdapter(Context context, List<Event> events) {
        super(context, R.layout.item_event, events);
    }

    @Override
    public int getItemViewType(int position) {

//        if (position % 6 == 0) {
//            return RowType.DATE.ordinal();
//        } else {
//            return RowType.EVENT.ordinal();
//        }

        if (position == 0) {
            return RowType.DATE.ordinal();
        }

        Event current = getItem(position);
        Event previous = getItem(position - 1);

        Calendar cal = Calendar.getInstance();

        Date currentDate = new Date(current.getStartTime().getValue());
        Date previousDate = new Date(previous.getStartTime().getValue());

        cal.setTime(currentDate);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentDay = cal.get(Calendar.DATE);

        cal.setTime(previousDate);
        int previousMonth = cal.get(Calendar.MONTH);
        int previousDay = cal.get(Calendar.DATE);

        if (currentMonth != previousMonth ||
                currentDay != previousDay) {
            return RowType.DATE.ordinal();
        }

        return RowType.EVENT.ordinal();
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
        Event event = getItem(position);
        EventViewHolder eventViewHolder;
        if (convertView == null) {
            eventViewHolder = new EventViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event, parent, false);
            eventViewHolder.tvHours = (TextView) convertView.findViewById(R.id.tvHours);
            eventViewHolder.tvTimeTo = (TextView) convertView.findViewById(R.id.tvTimeTo);
            eventViewHolder.tvTimeFrom = (TextView) convertView.findViewById(R.id.tvTimeFrom);
            eventViewHolder.tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);
            eventViewHolder.tvEventName = (TextView) convertView.findViewById(R.id.tvEventName);
            eventViewHolder.tvNonProfit = (TextView) convertView.findViewById(R.id.tvNonProfit);
            convertView.setTag(eventViewHolder);
        } else {
            eventViewHolder = (EventViewHolder) convertView.getTag();
        }

        eventViewHolder.tvHours.setText(getHours(event.getStartTime(), event.getEndTime()));
        eventViewHolder.tvTimeFrom.setText(Utils.getTimeString(event.getStartTime()) + " -");
        eventViewHolder.tvTimeTo.setText(Utils.getTimeString(event.getEndTime()));
        eventViewHolder.tvLocation.setText(event.getAddressNeighborhood() + ", " + Utils.getCityShort(event.getAddressCity()));
        eventViewHolder.tvEventName.setText(event.getTitle());
        eventViewHolder.tvNonProfit.setText(event.getOrganization().getName());

        return convertView;
    }

    private View getDateView(int position, View convertView, ViewGroup parent) {
        Event event = getItem(position);
        DateViewHolder dateViewHolder;
        if (convertView == null) {
            dateViewHolder = new DateViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_day, parent, false);
            dateViewHolder.tvDate = (TextView) convertView.findViewById(R.id.tvDate);
            dateViewHolder.tvHours = (TextView) convertView.findViewById(R.id.tvHours);
            dateViewHolder.tvTimeTo = (TextView) convertView.findViewById(R.id.tvTimeTo);
            dateViewHolder.tvTimeFrom = (TextView) convertView.findViewById(R.id.tvTimeFrom);
            dateViewHolder.tvLocation = (TextView) convertView.findViewById(R.id.tvLocation);
            dateViewHolder.tvEventName = (TextView) convertView.findViewById(R.id.tvEventName);
            dateViewHolder.tvNonProfit = (TextView) convertView.findViewById(R.id.tvNonProfit);
            convertView.setTag(dateViewHolder);
        } else {
            dateViewHolder = (DateViewHolder) convertView.getTag();
        }
        Date date = new Date(event.getStartTime().getValue());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int dayOfMonth = cal.get(Calendar.DATE);

        dateViewHolder.tvDate.setText(Utils.getDay(dayOfWeek) + ", " + Utils.getMonth(month) + " " + dayOfMonth);
        dateViewHolder.tvHours.setText(getHours(event.getStartTime(), event.getEndTime()));
        dateViewHolder.tvTimeFrom.setText(Utils.getTimeString(event.getStartTime()) + " -");
        dateViewHolder.tvTimeTo.setText(Utils.getTimeString(event.getEndTime()));
        dateViewHolder.tvLocation.setText(event.getAddressNeighborhood() + ", " + Utils.getCityShort(event.getAddressCity()));
        dateViewHolder.tvEventName.setText(event.getTitle());
        dateViewHolder.tvNonProfit.setText(event.getOrganization().getName());
        return convertView;
    }

    private String getHours(DateTime from, DateTime to) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(new Date(from.getValue()));
        int startHour = cal.get(Calendar.HOUR_OF_DAY);
        int startMin = cal.get(Calendar.MINUTE);

        cal.setTime(new Date(to.getValue()));
        int endHour = cal.get(Calendar.HOUR_OF_DAY);
        int endMin = cal.get(Calendar.MINUTE);

        int hourDiff = endHour - startHour;
        int minDiff = Math.abs(endMin - startMin);

        String hourDiffStr = hourDiff > 0 ? hourDiff + " h " : "";
        String minDiffStr = minDiff > 0 ? minDiff + " m" : "";

        return hourDiffStr + minDiffStr;

    }

    public enum RowType {
        DATE,
        EVENT
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
        TextView tvHours;
        TextView tvTimeTo;
        TextView tvTimeFrom;
        TextView tvLocation;
        TextView tvEventName;
        TextView tvNonProfit;
    }
}
