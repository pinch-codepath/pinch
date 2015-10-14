package com.pinch.backend.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

import com.pinch.backend.model.Constants;
import com.pinch.backend.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Api(
        name = "eventBeanApi",
        version = "v1",
        resource = "eventBean",
        namespace = @ApiNamespace(
                ownerDomain = "backend.pinch.com",
                ownerName = "backend.pinch.com",
                packagePath = ""
        )
)
public class EventEndpoint {

    private static final Logger logger = Logger.getLogger(EventEndpoint.class.getName());

    // support search by following:
    // 1. location - https://cloud.google.com/appengine/docs/java/datastore/geosearch?hl=en
    // 2. time - http://stackoverflow.com/questions/14171329/how-to-get-the-records-for-today-in-app-engine-java-datastore
    // 3. keyword
    // 4. org -

    @ApiMethod(name = "getAllEvents", path="getAllEvents")
    public List<Event> getAllEvents() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query(Constants.EVENT);
        PreparedQuery pq = datastore.prepare(query);

        List<Event> events = new ArrayList<>();
        for (Entity result : pq.asIterable()) {

        }
        logger.info("Calling getEvent method");
        return events;
    }

    @ApiMethod(name = "getAllOpenEvents", path="getAllOpenEvents")
    public List<Event> getAllOpenEvents() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query(Constants.EVENT);
        PreparedQuery pq = datastore.prepare(query);

        List<Event> events = new ArrayList<>();
        for (Entity result : pq.asIterable()) {

        }
        logger.info("Calling getEvent method");
        return events;
    }

    @ApiMethod(name = "insertEvent")
    public Event insertEvent(Event event) {
        logger.info("Calling insertEvent method");
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastoreService.beginTransaction();
        try {
            Key eventBeanParentKey = KeyFactory.createKey("EventBeanParent", event.getId());
            Entity eventEntity = new Entity("Event", event.getId(), eventBeanParentKey);
            eventEntity.setProperty("data", eventEntity);
            datastoreService.put(eventEntity);
            txn.commit();
        } finally {
            if(txn.isActive()) {
                txn.rollback();
            }
        }
        return event;
    }
}