package com.pinch.android.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class EventsArrayAdapter extends ArrayAdapter<String> {


    public EventsArrayAdapter(Context context, List<String> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }
}
