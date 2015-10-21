package com.pinch.backend.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

import com.pinch.backend.model.Constants;
import com.pinch.backend.model.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Api(
        name = "signUpEndpoint",
        version = "v1",
        resource = "signUp",
        namespace = @ApiNamespace(
                ownerDomain = "backend.pinch.com",
                ownerName = "backend.pinch.com",
                packagePath = ""
        )
)
public class SignUpEndpoint {
    private static final Logger logger = Logger.getLogger(SignUpEndpoint.class.getName());
    private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @ApiMethod(
            name = "signUp",
            httpMethod = ApiMethod.HttpMethod.POST
    )
    public void signUp(@Named("eventId") String eventId,
                       @Named("userId") String userId) throws EntityNotFoundException {
        Entity entity = new Entity(Constants.SIGNUP);
        entity.setProperty("eventId", eventId);
        entity.setProperty("userId", userId);
        Transaction txn = datastore.beginTransaction();
        try {
            datastore.put(entity);
            txn.commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    @ApiMethod(
            name = "delete",
            httpMethod = ApiMethod.HttpMethod.DELETE
    )
    public void delete(@Named("eventId") String eventId,
                       @Named("userId") String userId) throws EntityNotFoundException {
        Query query = new Query(Constants.SIGNUP);
        Query.Filter eventIdFilter = new Query.FilterPredicate("eventId",
                Query.FilterOperator.EQUAL,
                eventId);
        query.setFilter(eventIdFilter);
        Query.Filter userIdFilter = new Query.FilterPredicate("userId",
                Query.FilterOperator.EQUAL,
                userId);
        query.setFilter(userIdFilter);
        PreparedQuery pq = datastore.prepare(query);
        Transaction txn = datastore.beginTransaction();
        try {
            for (Entity entity : pq.asIterable()) {
                datastore.delete(entity.getKey());
                txn.commit();
            }
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    @ApiMethod(
            name = "eventsForUser"
    )
    public List<Event> eventsForUser(@Named("userId") String userId) throws EntityNotFoundException {
        Query query = new Query(Constants.SIGNUP);
        Query.Filter userIdFilter = new Query.FilterPredicate("userId",
                Query.FilterOperator.EQUAL,
                userId);
        query.setFilter(userIdFilter);
        PreparedQuery pq = datastore.prepare(query);
        List<Event> events = new ArrayList<>();
        for (Entity entity : pq.asIterable()) {
            events.add(Event.fromEntity(datastore, entity));
        }
        return events;
    }

}
