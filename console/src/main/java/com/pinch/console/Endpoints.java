package com.pinch.console;

import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import com.pinch.backend.affiliationEndpoint.AffiliationEndpoint;
import com.pinch.backend.eventEndpoint.EventEndpoint;
import com.pinch.backend.favoriteEndpoint.FavoriteEndpoint;
import com.pinch.backend.imageEndpoint.ImageEndpoint;
import com.pinch.backend.organizationEndpoint.OrganizationEndpoint;
import com.pinch.backend.signUpEndpoint.SignUpEndpoint;
import com.pinch.backend.userEndpoint.UserEndpoint;

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

    private UserEndpoint.Builder userEndpointBuilder = new UserEndpoint.Builder(new ApacheHttpTransport(),
            new JacksonFactory(),
            null)
            .setRootUrl("https://pinch-1097.appspot.com/_ah/api/")
            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
    public UserEndpoint userEndpoint = userEndpointBuilder.build();

    private SignUpEndpoint.Builder signupEndpointBuilder = new SignUpEndpoint.Builder(new ApacheHttpTransport(),
            new JacksonFactory(),
            null)
            .setRootUrl("https://pinch-1097.appspot.com/_ah/api/")
            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
    public SignUpEndpoint signUpEndpoint = signupEndpointBuilder.build();

    private AffiliationEndpoint.Builder affiliationEndpointBuilder = new AffiliationEndpoint.Builder(new ApacheHttpTransport(),
            new JacksonFactory(),
            null)
            .setRootUrl("https://pinch-1097.appspot.com/_ah/api/")
            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
    public AffiliationEndpoint affiliationEndpoint = affiliationEndpointBuilder.build();

    private FavoriteEndpoint.Builder favoriteEndpointBuilder = new FavoriteEndpoint.Builder(new ApacheHttpTransport(),
            new JacksonFactory(),
            null)
            .setRootUrl("https://pinch-1097.appspot.com/_ah/api/")
            .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                @Override
                public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                    abstractGoogleClientRequest.setDisableGZipContent(true);
                }
            });
    public FavoriteEndpoint favoriteEndpoint = favoriteEndpointBuilder.build();
}
