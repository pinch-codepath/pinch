package com.pinch.backend.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;

import com.googlecode.objectify.Key;
import com.pinch.backend.PushService;
import com.pinch.backend.model.Affiliation;
import com.pinch.backend.model.Constants;
import com.pinch.backend.model.Event;
import com.pinch.backend.model.SignUp;
import com.pinch.backend.model.User;

import java.util.List;
import java.util.logging.Logger;

import static com.pinch.backend.OfyService.ofy;

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
            name = "register",
            httpMethod = ApiMethod.HttpMethod.PUT
    )
    public SignUp insert(SignUp signUp) throws EntityNotFoundException {
        SignUp dsSignUp = lookup(signUp);
        if (dsSignUp != null) {
            return dsSignUp;
        } else {
            Long userId = signUp.getUserId();
            Long eventId = signUp.getEventId();
            User user = ofy()
                    .load()
                    .type(User.class)
                    .id(userId)
                    .now();
            com.google.appengine.api.datastore.Key eventKey = KeyFactory.createKey(Constants.EVENT, eventId);
            Event event = Event.fromEntity(datastore.get(eventKey));

            if(user != null && event != null) {
                Key<SignUp> key = ofy().save().entity(signUp).now();
                signUp.setId(key.getId());
                setSignUpAlert(user, event);
                return signUp;
            }
            return null;
        }
    }

    private void setSignUpAlert(User user, Event event) {
        String name = user.getName();
        List<Affiliation> affiliations = ofy()
                .load()
                .type(Affiliation.class)
                .filter("organizationId", event.getOrganization().getId())
                .list();
        for (Affiliation affiliation : affiliations) {
            long userId = affiliation.getUserId();
            User affiliate = ofy()
                    .load()
                    .type(User.class)
                    .id(userId)
                    .now();
            PushService.getInstance().sendPush(affiliate, "New sign up!", name + " signed up for " + event.getTitle() + "!");
        }
    }

    @ApiMethod(name = "unregister")
    public void delete(@Named("id") Long id) throws EntityNotFoundException {
        ofy().delete().type(SignUp.class).id(id).now();
    }

    @ApiMethod(name = "query")
    public SignUp query(SignUp signUp) throws EntityNotFoundException {
        return lookup(signUp);
    }

    private SignUp lookup(SignUp signUp) {
        return ofy()
                .load()
                .type(SignUp.class)
                .filter("eventId", signUp.getEventId())
                .filter("userId", signUp.getUserId())
                .first()
                .now();
    }
}
