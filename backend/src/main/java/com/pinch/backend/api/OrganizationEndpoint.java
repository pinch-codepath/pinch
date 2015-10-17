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
import com.pinch.backend.model.Organization;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Api(
        name = "organizationEndpoint",
        version = "v1",
        resource = "organization",
        namespace = @ApiNamespace(
                ownerDomain = "backend.pinch.com",
                ownerName = "backend.pinch.com",
                packagePath = ""
        )
)
public class OrganizationEndpoint {

    private static final Logger logger = Logger.getLogger(OrganizationEndpoint.class.getName());

    @ApiMethod(
            name = "get"
    )
    public Organization get(@Named("id") Long id) throws EntityNotFoundException {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = KeyFactory.createKey(Constants.ORGANIZATION, id);
        return Organization.fromEntity(datastore.get(key));
    }

    @ApiMethod(
            name = "getAll",
            path = "getAll"
    )
    public List<Organization> getAll() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query(Constants.ORGANIZATION);
        PreparedQuery pq = datastore.prepare(query);
        List<Organization> organizations = new ArrayList<>();
        for (Entity entity : pq.asIterable()) {
            organizations.add(Organization.fromEntity(entity));
        }
        return organizations;
    }

    @ApiMethod(
            name = "insert"
    )
    public Organization insert(Organization organization) {
        logger.info("Calling insertEvent method");
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastoreService.beginTransaction();
        Key key;
        try {
            Entity entity = Organization.toEntity(organization);
            key = datastoreService.put(entity);
            txn.commit();
            if(key != null) {
                organization.setId(key.getId());
            }
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
        return organization;
    }

    @ApiMethod(
            name = "delete",
            httpMethod = ApiMethod.HttpMethod.DELETE
    )
    public void delete(@Named("id") Long id) {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
        Transaction txn = datastoreService.beginTransaction();
        try {
            datastoreService.delete(KeyFactory.createKey(Constants.ORGANIZATION, id));
            txn.commit();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
        return;
    }

}
