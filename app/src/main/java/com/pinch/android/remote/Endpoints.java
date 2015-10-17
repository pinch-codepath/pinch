package com.pinch.android.remote;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import com.pinch.backend.eventEndpoint.EventEndpoint;
import com.pinch.backend.organizationEndpoint.OrganizationEndpoint;

public class Endpoints {
    public static final String ROOT_URL = "https://pinch-1097.appspot.com/_ah/api/";
    private static Endpoints ourInstance = new Endpoints();
    private EventEndpoint.Builder eventEndpointBuilder = new EventEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
            new AndroidJsonFactory(), null)
            .setRootUrl(ROOT_URL);
    public EventEndpoint eventEndpoint = eventEndpointBuilder.build();
    private OrganizationEndpoint.Builder organizationEndpointBuilder = new OrganizationEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
            new AndroidJsonFactory(), null)
            .setRootUrl("https://pinch-1097.appspot.com/_ah/api/");
    public OrganizationEndpoint organizationEndpoint = organizationEndpointBuilder.build();

    private Endpoints() {
    }

    public static Endpoints getInstance() {
        return ourInstance;
    }


}
