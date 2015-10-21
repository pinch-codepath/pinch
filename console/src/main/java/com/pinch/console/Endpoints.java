package com.pinch.console;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.pinch.backend.eventEndpoint.EventEndpoint;
import com.pinch.backend.imageEndpoint.ImageEndpoint;
import com.pinch.backend.organizationEndpoint.OrganizationEndpoint;
import java.io.IOException;

public class Endpoints {
    private static Endpoints ourInstance = new Endpoints();

    public static Endpoints getInstance() {
        return ourInstance;
    }

    private Endpoints() {
    }

    private EventEndpoint.Builder eventEndpointBuilder = new EventEndpoint.Builder(new ApacheHttpTransport(),
            new JacksonFactory(),
            null)
            .setRootUrl("https://pinch-1097.appspot.com/_ah/api/")
            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
    public EventEndpoint eventEndpoint = eventEndpointBuilder.build();

    private OrganizationEndpoint.Builder organizationEndpointBuilder = new OrganizationEndpoint.Builder(new ApacheHttpTransport(),
            new JacksonFactory(),
            null)
            .setRootUrl("https://pinch-1097.appspot.com/_ah/api/")
            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
    public OrganizationEndpoint organizationEndpoint = organizationEndpointBuilder.build();


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
}
