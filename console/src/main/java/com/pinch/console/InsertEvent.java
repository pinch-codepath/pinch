package com.pinch.console;

import com.pinch.backend.eventEndpoint.model.Event;

import java.io.IOException;

public class InsertEvent {
    public static void main(String[] args) throws IOException {
        Event event = new Event();
        event.setTitle("HelloWorld");
        Event returnedEvent = Endpoints.getInstance().eventEndpoint.insert(event).execute();
        if(returnedEvent != null) {
                System.out.println(returnedEvent.getId());
        }
    }
}
