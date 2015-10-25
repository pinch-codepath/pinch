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
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;

import com.pinch.backend.model.Constants;
import com.pinch.backend.model.Event;
import com.pinch.backend.model.Organization;
import com.pinch.backend.model.Search;
import com.pinch.backend.model.SignUp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static com.pinch.backend.api.SearchIndex.EVENT_INDEX;

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
    private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @ApiMethod(
            name = "get"
    )
    public Event get(@Named("id") Long id) throws EntityNotFoundException {
        Key key = KeyFactory.createKey(Constants.EVENT, id);
        return Event.fromEntity(datastore, datastore.get(key));
    }

    @ApiMethod(
            name = "getAll"
    )
    public Collection<Event> getAll() throws EntityNotFoundException {
        Query query = new Query(Constants.EVENT);
        PreparedQuery pq = datastore.prepare(query);
        List<Event> events = new ArrayList<>();
        for (Entity entity : pq.asIterable()) {
            events.add(Event.fromEntity(datastore, entity));
        }
        return events;
    }

    @ApiMethod(
            name = "getAllFuture",
            path = "getAllFuture"
    )
    public Collection<Event> getAllFuture() throws EntityNotFoundException {
        Filter startTimeFilter = new FilterPredicate("startTime",
                FilterOperator.GREATER_THAN_OR_EQUAL,
                new Date());
        Query query = new Query(Constants.EVENT)
                .setFilter(startTimeFilter)
                .addSort("startTime", Query.SortDirection.ASCENDING);
        PreparedQuery pq = datastore.prepare(query);
        List<Event> events = new ArrayList<>();
        for (Entity entity : pq.asIterable()) {
            events.add(Event.fromEntity(datastore, entity));
        }
        return events;
    }

    @ApiMethod(
            name = "getSignedUpEventsForUser",
            path = "/event/signups"
    )
    public List<Event> getSignedUpEventsForUser(@Named("userId") Long userId) throws EntityNotFoundException {
        Query query = new Query(Constants.SIGNUP);
        Query.Filter userIdFilter = new Query.FilterPredicate("userId",
                Query.FilterOperator.EQUAL,
                userId);
        query.setFilter(userIdFilter);
        PreparedQuery pq = datastore.prepare(query);
        List<Event> events = new ArrayList<>();
        for (Entity entity : pq.asIterable()) {
            SignUp signUp = SignUp.fromEntity(entity);
            long eventId = signUp.getEventId();
            Key eventKey = KeyFactory.createKey(Constants.EVENT, eventId);
            Entity eventEntity = datastore.get(eventKey);
            events.add(Event.fromEntity(datastore, eventEntity));
        }
        return events;
    }

    @ApiMethod(
            name = "insert"
    )
    public Event insert(@Named("organizationId") long organizationId, Event event) throws EntityNotFoundException {
        logger.info("Calling insertEvent method");
        Transaction txn = datastore.beginTransaction();
        Key key = null;
        Entity entity = Event.toEntity(organizationId, event);
        try {
            key = datastore.put(entity);
            txn.commit();
        } finally {
            if (key != null) {
                event.setId(key.getId());
            }
            if (txn.isActive()) {
                txn.rollback();
            } else if (key != null) {
                Key parentKey = KeyFactory.createKey(Constants.ORGANIZATION, organizationId);
                Entity orgEntity = datastore.get(parentKey);
                Organization organization = Organization.fromEntity(orgEntity);
                Document doc = Document.newBuilder()
                        .setId(key.getId() + "")
                        .addField(Field.newBuilder().setName("title").setText(event.getTitle()))
                        .addField(Field.newBuilder().setName("addressCity").setText(event.getAddressCity()))
                        .addField(Field.newBuilder().setName("addressNeighborhood").setText(event.getAddressNeighborhood()))
                        .addField(Field.newBuilder().setName("addressState").setText(event.getAddressState()))
                        .addField(Field.newBuilder().setName("addressStreet").setText(event.getAddressStreet()))
                        .addField(Field.newBuilder().setName("skill1").setText(event.getSkill1()))
                        .addField(Field.newBuilder().setName("skill2").setText(event.getSkill2()))
                        .addField(Field.newBuilder().setName("skill3").setText(event.getSkill3()))
                        .addField(Field.newBuilder().setName("description").setText(event.getDescription()))
                        .addField(Field.newBuilder().setName("url").setText(event.getUrl()))
                        .addField(Field.newBuilder().setName("orgName").setText(organization.getName()))
                        .addField(Field.newBuilder().setName("orgAddress").setText(organization.getAddress()))
                        .addField(Field.newBuilder().setName("orgUrl").setText(organization.getUrl()))
                        .build();
                EVENT_INDEX.indexDocument(doc);
            }
        }
        return event;
    }

    @ApiMethod(
            name = "search"
    )
    public Collection<Event> search(Search search) throws EntityNotFoundException {
        Query query = new Query(Constants.EVENT)
                .addSort("startTime", Query.SortDirection.ASCENDING);

        if (search.getStartTime() != null) {
            Filter startTimeFilter = new FilterPredicate("startTime",
                    FilterOperator.GREATER_THAN_OR_EQUAL,
                    search.getStartTime());
            query.setFilter(startTimeFilter);
        }

        if (search.getEndTime() != null) {
            Filter endTimeFilter = new FilterPredicate("endTime",
                    FilterOperator.LESS_THAN_OR_EQUAL,
                    search.getEndTime());
            query.setFilter(endTimeFilter);
        }

        if (search.getCurrentLocation() != null && search.getDistanceInMeters() > 0) {
            Filter geoFilter = new Query.StContainsFilter("location",
                    new Query.GeoRegion.Circle(search.getCurrentLocation(),
                            search.getDistanceInMeters()));
            query.setFilter(geoFilter);
        }

        PreparedQuery pq = datastore.prepare(query);
        List<Event> events = new ArrayList<>();
        for (Entity entity : pq.asIterable()) {
            events.add(Event.fromEntity(datastore, entity));
        }

        if (search.getText() != null) {
            Set<Long> eventIds = new HashSet<>();
            Results<ScoredDocument> scoredDocuments = EVENT_INDEX.retrieveDocuments(search.getText());
            for (ScoredDocument document : scoredDocuments) {
                String id = document.getId();
                eventIds.add(Long.parseLong(id));
            }

            List<Event> newEvents = new ArrayList<>();
            for (Event event : events) {
                if (eventIds.contains(event.getId())) {
                    newEvents.add(event);
                }
            }
            return newEvents;
        }
        return events;
    }

    @ApiMethod(
            name = "delete",
            httpMethod = ApiMethod.HttpMethod.DELETE
    )
    public void delete(@Named("id") Long id) {
        Transaction txn = datastore.beginTransaction();
        try {
            Key key = KeyFactory.createKey(Constants.EVENT, id);
            datastore.delete(key);
            txn.commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            } else {
                EVENT_INDEX.deleteDocumentFromIndex(id + "");
            }
        }
    }
}
