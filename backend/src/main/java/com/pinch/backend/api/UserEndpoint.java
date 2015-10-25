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
import com.google.appengine.api.datastore.Transaction;

import com.pinch.backend.model.Constants;
import com.pinch.backend.model.User;

import java.util.logging.Logger;

@Api(
        name = "userEndpoint",
        version = "v1",
        resource = "user",
        namespace = @ApiNamespace(
                ownerDomain = "backend.pinch.com",
                ownerName = "backend.pinch.com",
                packagePath = ""
        )
)
public class UserEndpoint {
    private static final Logger logger = Logger.getLogger(EventEndpoint.class.getName());
    private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @ApiMethod(
            name = "get"
    )
    public User get(@Named("id") long id) throws EntityNotFoundException {
        Key key = KeyFactory.createKey(Constants.USER, id);
        Entity entity = datastore.get(key);
        return User.fromEntity(entity);
    }

    @ApiMethod(
            name = "insertIfMissing",
            httpMethod = ApiMethod.HttpMethod.PUT
    )
    public User insertIfMissing(User user) throws EntityNotFoundException {
        Query.Filter idFilter = new Query.FilterPredicate("id",
                Query.FilterOperator.EQUAL,
                user.getId());
        Query.Filter authSourceFilter = new Query.FilterPredicate("authSource",
                Query.FilterOperator.EQUAL,
                user.getAuthSource());
        Query query = new Query(Constants.USER)
                .setFilter(idFilter)
                .setFilter(authSourceFilter);
        PreparedQuery pq = datastore.prepare(query);
        for (Entity entity : pq.asIterable()) return User.fromEntity(entity);
        user.setKey(0);
        Entity entity = User.toEntity(user);
        Transaction txn = datastore.beginTransaction();
        Key key = null;
        try {
            key = datastore.put(entity);
            txn.commit();
        } finally {
            if (key != null) user.setKey(key.getId());
            if (txn.isActive()) {
                txn.rollback();
            }
        }
        return user;
    }

    @ApiMethod(
            name = "delete",
            httpMethod = ApiMethod.HttpMethod.DELETE
    )
    public void delete(@Named("id") Long id) {
        Transaction txn = datastore.beginTransaction();
        try {
            Key key = KeyFactory.createKey(Constants.USER, id);
            datastore.delete(key);
            txn.commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }
}
