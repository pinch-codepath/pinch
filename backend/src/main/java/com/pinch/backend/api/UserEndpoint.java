package com.pinch.backend.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.EntityNotFoundException;

import com.googlecode.objectify.Key;
import com.pinch.backend.model.Affiliation;
import com.pinch.backend.model.Favorite;
import com.pinch.backend.model.Organization;
import com.pinch.backend.model.SignUp;
import com.pinch.backend.model.User;

import java.util.ArrayList;
import java.util.List;

import static com.pinch.backend.OfyService.ofy;

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

    @ApiMethod(
            name = "get"
    )
    public User get(@Named("id") long id) throws EntityNotFoundException {
        User user = ofy()
                .load()
                .type(User.class)
                .id(id)
                .now();
        if(user != null) {
            user.setAffiliations(getAffiliations(user.getId()));
        }
        return user;
    }

    @ApiMethod(
            name = "getByAuthSource",
            path = "/user/getByAuthSource"
    )
    public User getByAuthSource(@Named("authSource") String authSource, @Named("authId") String authId) throws EntityNotFoundException {
        User user = ofy()
                .load()
                .type(User.class)
                .filter("authId", authId)
                .filter("authSource", authSource)
                .first()
                .now();
        if(user != null) {
            user.setAffiliations(getAffiliations(user.getId()));
        }
        return user;
    }

    @ApiMethod(
            name="updateGCMToekn"
    )
    public User updateGCMToekn(@Named("userId") long userId, @Named("gcmToken") String gcmToken) throws EntityNotFoundException {
        User user = ofy()
                .load()
                .type(User.class)
                .id(userId)
                .now();
        user.setGcmToken(gcmToken);
        ofy().save().entity(user).now();
        return user;
    }

    @ApiMethod(
            name = "insertIfMissing",
            httpMethod = ApiMethod.HttpMethod.PUT
    )
    public User insertIfMissing(User user) throws EntityNotFoundException {
        User dsUser = ofy()
                .load()
                .type(User.class)
                .filter("authId", user.getAuthId())
                .filter("authSource", user.getAuthSource())
                .first()
                .now();
        if (dsUser != null) {
            dsUser.setAffiliations(getAffiliations(dsUser.getId()));
            return dsUser;
        } else {
            Key<User> key = ofy().save().entity(user).now();
            user.setId(key.getId());
            return user;
        }
    }

    private List<Organization> getAffiliations(Long userId) {
        List<Affiliation> affiliations = ofy()
                .load()
                .type(Affiliation.class)
                .filter("userId", userId)
                .list();
        List<Organization> organizations = new ArrayList<>();
        for (Affiliation affiliation : affiliations) {
            long organizationId = affiliation.getOrganizationId();
            Organization organization = ofy()
                    .load()
                    .type(Organization.class)
                    .id(organizationId)
                    .now();
            if (organization != null) {
                organizations.add(organization);
            }
        }
        return organizations;
    }

    @ApiMethod(
            name = "getAffiliationsForUser",
            path = "/user/affiliations"
    )
    public List<Organization> getAffiliationsForUser(@Named("userId") Long userId) throws EntityNotFoundException {
        return getAffiliations(userId);
    }

    @ApiMethod(
            name = "getFavoritesForUser",
            path = "/user/favorites"
    )
    public List<Organization> getFavoritesForUser(@Named("userId") Long userId) throws EntityNotFoundException {
        return getFavorites(userId);
    }

    private List<Organization> getFavorites(@Named("userId") Long userId) {
        List<Favorite> favorites = ofy()
                .load()
                .type(Favorite.class)
                .filter("userId", userId)
                .list();
        List<Organization> organizations = new ArrayList<>();
        for (Favorite favorite : favorites) {
            long organizationId = favorite.getOrganizationId();
            Organization organization = ofy()
                    .load()
                    .type(Organization.class)
                    .id(organizationId)
                    .now();
            if (organization != null) {
                organizations.add(organization);
            }
        }
        return organizations;
    }

    @ApiMethod(name = "delete")
    public void delete(@Named("id") Long id) {
        // delete signups
        List<SignUp> signUps = ofy()
                .load()
                .type(SignUp.class)
                .filter("userId", id)
                .list();
        List<Long> ids = new ArrayList<>();
        for (SignUp signUp: signUps) {
            ids.add(signUp.getId());
        }
        ofy().delete().type(SignUp.class).ids(ids);

        // delete user
        ofy().delete().type(User.class).id(id).now();
    }
}
