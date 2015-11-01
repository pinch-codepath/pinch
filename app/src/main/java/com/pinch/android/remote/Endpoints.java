package com.pinch.android.remote;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.pinch.backend.eventEndpoint.EventEndpoint;
import com.pinch.backend.favoriteEndpoint.FavoriteEndpoint;
import com.pinch.backend.imageEndpoint.ImageEndpoint;
import com.pinch.backend.organizationEndpoint.OrganizationEndpoint;
import com.pinch.backend.signUpEndpoint.SignUpEndpoint;
import com.pinch.backend.userEndpoint.UserEndpoint;

import java.io.IOException;

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

    private UserEndpoint.Builder userEndpointBuilder = new UserEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
            new AndroidJsonFactory(), null)
            .setRootUrl(ROOT_URL);
    public UserEndpoint userEndpoint = userEndpointBuilder.build();

    private SignUpEndpoint.Builder signUpEndpointBuilder = new SignUpEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
            new AndroidJsonFactory(), null)
            .setRootUrl(ROOT_URL);
    public SignUpEndpoint signUpEndpoint = signUpEndpointBuilder.build();

    private FavoriteEndpoint.Builder favoriteEndpointBuilder = new FavoriteEndpoint.Builder(AndroidHttp.newCompatibleTransport(),
            new AndroidJsonFactory(), null)
            .setRootUrl(ROOT_URL);
    public FavoriteEndpoint favoriteEndpoint = favoriteEndpointBuilder.build();

    private ImageEndpoint.Builder imageEndpointBuilder = new ImageEndpoint.Builder(new ApacheHttpTransport(),
            new JacksonFactory(),
            null)
            .setRootUrl("https://pinch-1097.appspot.com/_ah/api/")
            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
    public ImageEndpoint imageEndpoint = imageEndpointBuilder.build();


    private Endpoints() {
    }

    public static Endpoints getInstance() {
        return ourInstance;
    }


}
