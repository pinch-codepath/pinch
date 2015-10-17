package com.pinch.console;

import com.pinch.backend.eventEndpoint.model.Event;

import java.io.IOException;
import java.util.List;

public class GetAllFutureEvents {
    public static void main(String[] args) throws IOException {
        List<Event> events = Endpoints.getInstance().eventEndpoint.getAllFuture().execute().getItems();
        if(events != null) {
            for (Event event: events){
                System.out.println(event);
            }
        }
    }
}
