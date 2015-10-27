package com.pinch.backend.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.EntityNotFoundException;

import com.googlecode.objectify.Key;
import com.pinch.backend.model.Affiliation;

import static com.pinch.backend.OfyService.ofy;

@Api(
        name = "affiliationEndpoint",
        version = "v1",
        resource = "affiliation",
        namespace = @ApiNamespace(
                ownerDomain = "backend.pinch.com",
                ownerName = "backend.pinch.com",
                packagePath = ""
        )
)
public class AffiliationEndpoint {
    @ApiMethod(
            name = "register",
            httpMethod = ApiMethod.HttpMethod.PUT
    )
    public Affiliation insert(Affiliation affiliation) throws EntityNotFoundException {
        Affiliation dsAffiliation = lookup(affiliation);
        if (dsAffiliation != null) {
            return dsAffiliation;
        } else {
            Key<Affiliation> key = ofy().save().entity(affiliation).now();
            affiliation.setId(key.getId());
            return affiliation;
        }
    }

    @ApiMethod(name = "unregister")
    public void delete(@Named("id") Long id) throws EntityNotFoundException {
        ofy().delete().type(Affiliation.class).id(id).now();
    }

    @ApiMethod(name = "query")
    public Affiliation query(Affiliation affiliation) throws EntityNotFoundException {
        return lookup(affiliation);
    }

    private Affiliation lookup(Affiliation affiliation) {
        return ofy()
                .load()
                .type(Affiliation.class)
                .filter("organizationId", affiliation.getOrganizationId())
                .filter("userId", affiliation.getUserId())
                .first()
                .now();
    }

}
