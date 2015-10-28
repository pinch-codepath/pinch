package com.pinch.backend.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.EntityNotFoundException;

import com.googlecode.objectify.Key;
import com.pinch.backend.model.SignUp;

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

    @ApiMethod(
            name = "register",
            httpMethod = ApiMethod.HttpMethod.PUT
    )
    public SignUp insert(SignUp signUp) throws EntityNotFoundException {
        SignUp dsSignUp = lookup(signUp);
        if (dsSignUp != null) {
            return dsSignUp;
        } else {
            Key<SignUp> key = ofy().save().entity(signUp).now();
            signUp.setId(key.getId());
            return signUp;
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
