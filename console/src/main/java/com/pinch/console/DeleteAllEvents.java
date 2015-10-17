package com.pinch.console;

import com.pinch.backend.eventEndpoint.EventEndpoint;
import com.pinch.backend.eventEndpoint.model.Event;

import java.io.IOException;
import java.util.List;

public class DeleteAllEvents {

    public static void main(String[] args) throws IOException {
        EventEndpoint endpoint = Endpoints.getInstance().eventEndpoint;
        List<Event> events = endpoint.getAll().execute().getItems();
        if(events != null) {
            for (Event event: events){
                endpoint.delete(event.getId()).execute();
            }
        }
    }
}
