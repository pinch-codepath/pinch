package com.pinch.backend.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.EntityNotFoundException;

import com.pinch.backend.model.Organization;

import java.util.List;
import java.util.logging.Logger;

import static com.pinch.backend.OfyService.ofy;

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
        return ofy()
                .load()
                .type(Organization.class)
                .id(id)
                .now();
    }

    @ApiMethod(
            name = "getAll",
            path = "getAll"
    )
    public List<Organization> getAll() {
        return ofy()
                .load()
                .type(Organization.class)
                .list();
    }

    @ApiMethod(
            name = "insert"
    )
    public Organization insert(Organization organization) {
        Organization dsOrganization = ofy()
                .load()
                .type(Organization.class)
                .filter("name", organization.getName())
                .first()
                .now();
        if (dsOrganization != null) {
            return dsOrganization;
        } else {
            com.googlecode.objectify.Key<Organization> key = ofy().save().entity(organization).now();
            organization.setId(key.getId());
            return organization;
        }
    }

    @ApiMethod(
            name = "delete",
            httpMethod = ApiMethod.HttpMethod.DELETE
    )
    public void delete(@Named("id") Long id) {
        ofy().delete().type(Organization.class).id(id).now();
    }

}
