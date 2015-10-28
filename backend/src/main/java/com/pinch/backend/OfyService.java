package com.pinch.backend;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.pinch.backend.model.Affiliation;
import com.pinch.backend.model.Favorite;
import com.pinch.backend.model.Organization;
import com.pinch.backend.model.SignUp;
import com.pinch.backend.model.User;

public class OfyService {

    static {
        ObjectifyService.register(Affiliation.class);
        ObjectifyService.register(Favorite.class);
        ObjectifyService.register(Organization.class);
        ObjectifyService.register(SignUp.class);
        ObjectifyService.register(User.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
