package com.pinch.backend.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.EntityNotFoundException;

import com.googlecode.objectify.Key;
import com.pinch.backend.model.Favorite;

import static com.pinch.backend.OfyService.ofy;

@Api(
        name = "favoriteEndpoint",
        version = "v1",
        resource = "favorite",
        namespace = @ApiNamespace(
                ownerDomain = "backend.pinch.com",
                ownerName = "backend.pinch.com",
                packagePath = ""
        )
)
public class FavoriteEndpoint {
    @ApiMethod(
            name = "register",
            httpMethod = ApiMethod.HttpMethod.PUT
    )
    public Favorite insert(Favorite favorite) throws EntityNotFoundException {
        Favorite dsFavorite = lookup(favorite);
        if (dsFavorite != null) {
            return dsFavorite;
        } else {
            Key<Favorite> key = ofy().save().entity(favorite).now();
            favorite.setId(key.getId());
            return favorite;
        }
    }

    @ApiMethod(name = "unregister")
    public void delete(@Named("id") Long id) throws EntityNotFoundException {
        ofy().delete().type(Favorite.class).id(id).now();
    }

    @ApiMethod(name = "query")
    public Favorite query(Favorite favorite) throws EntityNotFoundException {
        return lookup(favorite);
    }

    private Favorite lookup(Favorite favorite) {
        return ofy()
                .load()
                .type(Favorite.class)
                .filter("organizationId", favorite.getOrganizationId())
                .filter("userId", favorite.getUserId())
                .first()
                .now();
    }

}
