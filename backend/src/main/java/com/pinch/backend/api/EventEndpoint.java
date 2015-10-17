package com.pinch.backend.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;

import com.pinch.backend.model.Constants;
import com.pinch.backend.model.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Api(
        name = "eventEndpoint",
        version = "v1",
        resource = "event",
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

    @ApiMethod(
            name = "get"
    )
    public Event get(@Named("id") Long id) throws EntityNotFoundException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = KeyFactory.createKey(Constants.EVENT, id);
       return Event.fromEntity(datastore.get(key));
    }

    @ApiMethod(
            name = "getAll"
    )
    public List<Event> getAll() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query(Constants.EVENT);
        PreparedQuery pq = datastore.prepare(query);
        List<Event> events = new ArrayList<>();
        for (Entity entity : pq.asIterable()) {
            events.add(Event.fromEntity(entity));
        }
        return events;
    }

    @ApiMethod(
            name = "getAllOpenEvents",
            path = "getAllOpenEvents"
    )
    public List<Event> getAllOpenEvents() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Filter startTimeFilter = new FilterPredicate("startTime",
                FilterOperator.GREATER_THAN_OR_EQUAL,
                new Date());
        Query query = new Query(Constants.EVENT)
                .setFilter(startTimeFilter)
                .addSort("startTime", Query.SortDirection.ASCENDING);
        PreparedQuery pq = datastore.prepare(query);
        List<Event> events = new ArrayList<>();
        for (Entity entity : pq.asIterable()) {
            events.add(Event.fromEntity(entity));
        }
        return events;
    }

    @ApiMethod(
            name = "insert"
    )
    public Event insert(Event event) {
        logger.info("Calling insertEvent method");
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastoreService.beginTransaction();
        try {
            Entity entity = Event.toEntity(event);
            datastoreService.put(entity);
            txn.commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
        return event;
    }

    @ApiMethod(
            name = "delete",
            httpMethod = ApiMethod.HttpMethod.DELETE
    )
    public void delete(@Named("id") Long id) {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastoreService.beginTransaction();
        try {
            datastoreService.delete(KeyFactory.createKey(Constants.EVENT, id));
            txn.commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
        return;
    }

}