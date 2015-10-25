package com.pinch.backend.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

import com.pinch.backend.model.Constants;
import com.pinch.backend.model.SignUp;

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
            name = "insert",
            httpMethod = ApiMethod.HttpMethod.PUT
    )
    public SignUp insert(SignUp signUp) throws EntityNotFoundException {
        Query query = new Query(Constants.SIGNUP);
        Query.Filter eventIdFilter = new Query.FilterPredicate("eventId",
                Query.FilterOperator.EQUAL,
                signUp.getEventId());
        query.setFilter(eventIdFilter);
        Query.Filter userIdFilter = new Query.FilterPredicate("userId",
                Query.FilterOperator.EQUAL,
                signUp.getUserId());
        query.setFilter(userIdFilter);
        PreparedQuery pq = datastore.prepare(query);
        for (Entity entity : pq.asIterable()) {
            return SignUp.fromEntity(entity);
        }
        Entity entity = SignUp.toEntity(signUp);
        Transaction txn = datastore.beginTransaction();
        Key key = null;
        try {
            key = datastore.put(entity);
            txn.commit();
        } finally {
            if (txn.isActive() || key == null) {
                txn.rollback();
            } else {
                signUp.setId(key.getId());
            }
        }
        return signUp;
    }

    @ApiMethod(
            name = "delete"
    )
    public void delete(SignUp signUp) throws EntityNotFoundException {
        Query query = new Query(Constants.SIGNUP);
        Query.Filter eventIdFilter = new Query.FilterPredicate("eventId",
                Query.FilterOperator.EQUAL,
                signUp.getEventId());
        query.setFilter(eventIdFilter);
        Query.Filter userIdFilter = new Query.FilterPredicate("userId",
                Query.FilterOperator.EQUAL,
                signUp.getUserId());
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
            name = "isSignedUp"
    )
    public SignUp isSignedUp(SignUp signUp) throws EntityNotFoundException {
        Query query = new Query(Constants.SIGNUP);
        Query.Filter eventIdFilter = new Query.FilterPredicate("eventId",
                Query.FilterOperator.EQUAL,
                signUp.getEventId());
        query.setFilter(eventIdFilter);
        Query.Filter userIdFilter = new Query.FilterPredicate("userId",
                Query.FilterOperator.EQUAL,
                signUp.getUserId());
        query.setFilter(userIdFilter);
        PreparedQuery pq = datastore.prepare(query);
        for (Entity entity : pq.asIterable()) {
            return SignUp.fromEntity(entity);
        }
        return null;
    }
}
