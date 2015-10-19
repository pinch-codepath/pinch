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
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;
import com.google.appengine.repackaged.com.google.common.base.Pair;

import com.pinch.backend.model.Constants;
import com.pinch.backend.model.Event;
import com.pinch.backend.model.Organization;
import com.pinch.backend.model.Search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
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

    // support search by following:
    // 1. location - https://cloud.google.com/appengine/docs/java/datastore/geosearch?hl=en
    // 2. time - http://stackoverflow.com/questions/14171329/how-to-get-the-records-for-today-in-app-engine-java-datastore
    // 3. keyword
    // 4. org -

    @ApiMethod(
            name = "get"
    )
    public Event get(@Named("orgId") Long orgId, @Named("id") Long id) throws EntityNotFoundException {
        Key parentKey = KeyFactory.createKey(Constants.ORGANIZATION, orgId);
        Key key = KeyFactory.createKey(parentKey, Constants.EVENT, id);
        return fromEntity(datastore.get(key));
    }

    @ApiMethod(
            name = "getAll"
    )
    public Collection<Event> getAll() throws EntityNotFoundException {
        Query query = new Query(Constants.EVENT);
        PreparedQuery pq = datastore.prepare(query);
        List<Event> events = new ArrayList<>();
        for (Entity entity : pq.asIterable()) {
            events.add(fromEntity(entity));
        }
        return events;
    }

    private Event fromEntity(Entity entity) throws EntityNotFoundException {
        Key parentKey = entity.getKey().getParent();
        Entity parentEntity = datastore.get(parentKey);
        Organization organization = Organization.fromEntity(parentEntity);
        Event event = Event.fromEntity(entity);
        event.setOrganization(organization);
        return event;
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
            events.add(fromEntity(entity));
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
        Key parentKey = KeyFactory.createKey(Constants.ORGANIZATION, organizationId);
        Entity entity = Event.toEntity(parentKey, event);
        try {
            key = datastore.put(entity);
            txn.commit();
        } finally {
            if(key != null) {
                event.setId(key.getId());
            }
            if (txn.isActive()) {
                txn.rollback();
            } else if(key != null){
                Entity orgEntity = datastore.get(parentKey);
                Organization organization = Organization.fromEntity(orgEntity);
                Document doc = Document.newBuilder()
                        .setId(EVENT_INDEX.getDocumentId(organization.getId(), key.getId()))
                        .addField(Field.newBuilder().setName("title").setText(event.getTitle()))
                        .addField(Field.newBuilder().setName("addressCity").setText(event.getAddressCity()))
                        .addField(Field.newBuilder().setName("addressNeighborhood").setText(event.getAddressNeighborhood()))
                        .addField(Field.newBuilder().setName("addressState").setText(event.getAddressState()))
                        .addField(Field.newBuilder().setName("addressStreet").setText(event.getAddressStreet()))
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
        logger.log(Level.WARNING, "HERE!!");

        Query query = new Query(Constants.EVENT)
                .addSort("startTime", Query.SortDirection.ASCENDING);

        if(search.getStartTime() != null) {
            Filter startTimeFilter = new FilterPredicate("startTime",
                    FilterOperator.GREATER_THAN_OR_EQUAL,
                    search.getStartTime());
            query.setFilter(startTimeFilter);
        }
        PreparedQuery pq = datastore.prepare(query);
        List<Event> events = new ArrayList<>();
        for (Entity entity : pq.asIterable()) {
            events.add(fromEntity(entity));
        }

        logger.log(Level.WARNING, search.toString());
        if(search.getText() != null) {
            Set<Pair<Long, Long>> eventIds = new HashSet<>();
            Results<ScoredDocument> scoredDocuments = EVENT_INDEX.retrieveDocuments(search.getText());
            for (ScoredDocument document : scoredDocuments) {
                String id = document.getId();
                int slash = id.indexOf("/");
                String organizationId = id.substring(0, slash);
                String eventId = id.substring(slash + 1);
                eventIds.add(Pair.of(Long.parseLong(organizationId), Long.parseLong(eventId)));
            }

            logger.log(Level.WARNING, eventIds.toString());
            List<Event> newEvents = new ArrayList<>();
            for(Event event : events) {
                if(eventIds.contains(Pair.of(event.getOrganization().getId(), event.getId()))){
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
    public void delete(@Named("orgId") Long orgId, @Named("id") Long id) {
        Transaction txn = datastore.beginTransaction();
        try {
            Key parentKey = KeyFactory.createKey(Constants.ORGANIZATION, orgId);
            Key key = KeyFactory.createKey(parentKey, Constants.EVENT, id);
            datastore.delete(key);
            txn.commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            } else {
                EVENT_INDEX.deleteDocumentFromIndex(EVENT_INDEX.getDocumentId(orgId, id));
            }
        }
    }
}

enum SearchIndex {
    EVENT_INDEX;

    private IndexSpec indexSpec = IndexSpec.newBuilder().setName("event_index").build();
    private Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);


    String getDocumentId(@Named("orgId") Long orgId, @Named("id") Long id) {
        return orgId + "/" + id;
    }

    public void indexDocument(Document document) {
        try {
            index.put(document);
        } catch (PutException e) {
            if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
            }
        }
    }

    public Document retrieveDocument(String documentId) {
        //Retrieve the Record from the Index
        return index.get(documentId);
    }

    public Results<ScoredDocument> retrieveDocuments(String searchText) {
        return index.search(searchText);
    }

    public void deleteDocumentFromIndex(String documentId) {
        index.delete(documentId);
    }

}

