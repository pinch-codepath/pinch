package com.pinch.console;

import com.google.api.client.util.DateTime;

import com.pinch.backend.eventEndpoint.model.Event;
import com.pinch.backend.eventEndpoint.model.Search;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class SearchEvents {
    public static void main(String[] args) throws IOException {
        Search search = new Search();
        search.setStartTime(new DateTime(new Date()));
        search.setText("Sanctuary");
        List<Event> events = Endpoints.getInstance().eventEndpoint.search(search).execute().getItems();
        if(events != null) {
            for (Event event: events){
                System.out.println(event);
            }
        }
    }
}
