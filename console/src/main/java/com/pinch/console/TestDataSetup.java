package com.pinch.console;

import com.pinch.backend.eventEndpoint.EventEndpoint;
import com.pinch.backend.eventEndpoint.model.Event;
import com.pinch.backend.organizationEndpoint.OrganizationEndpoint;
import com.pinch.backend.organizationEndpoint.model.GeoPt;
import com.pinch.backend.organizationEndpoint.model.Organization;

import java.io.IOException;
import java.util.List;

public class TestDataSetup {

    private static OrganizationEndpoint organizationEndpoint = Endpoints.getInstance().organizationEndpoint;
    private static EventEndpoint eventEndpoint = Endpoints.getInstance().eventEndpoint;

    public static void main(String[] args) throws IOException {
        deleteAllEvents();

        deleteAllOrgs();

        insertOrg("Curry Senior Center", "333 Turk Street San Francisco, CA 94102", 37.782582f, -122.414442f, "http://curryseniorcenter.org/how-to-help/volunteer/");
        insertOrg("Next Door Shelter", "Episcopal Community Services 165 8th Street, 3rd Floor San Francisco, CA 94103", 37.776814f, -122.412128f, "http://ecs-sf.org/getinvolved/volunteer.html");
        insertOrg("Sanctuary Center", "Episcopal Community Services 165 8th Street, 3rd Floor San Francisco, CA 94103", 37.776814f, -122.412128f, "http://ecs-sf.org/getinvolved/volunteer.html");
        insertOrg("Glide Memorial", "330 Ellis Street, San Francisco, CA 94102", 37.785268f, -122.411432f, "http://www.glide.org/serveameal");
        insertOrg("SF Marin Food Bank", "900 Pennsylvania Avenue San Francisco, CA 94107", 37.754461f, -122.393679f, "http://www.sfmfoodbank.org/volunteer-opportunities");
        insertOrg("Micro Mentor", "Mercy Corps 45 SW Ankeny Street Portland, OR 97204", 45.522617f, -122.670751f, "http://www.micromentor.org/");
    }

    private static void insertOrg(String name, String address, float latitude, float longitude, String url) throws IOException {
        GeoPt geoPt = new GeoPt();
        geoPt.setLatitude(latitude);
        geoPt.setLongitude(longitude);
        Organization organization = new Organization();
        organization.setName(name);
        organization.setAddress(address);
        organization.setLocation(geoPt);
        organization.setUrl(url);
        Organization returnedOrg = organizationEndpoint.insert(organization).execute();
        if(returnedOrg != null) {
            System.out.println("Inserted organization: " + returnedOrg);
        }
    }

    private static void deleteAllOrgs() throws IOException {
        List<Organization> organizations = organizationEndpoint.getAll().execute().getItems();
        if(organizations != null) {
            for (Organization organization: organizations){
                organizationEndpoint.delete(organization.getId()).execute();
            }
        }
        System.out.println("Deleted all orgs");
    }

    private static void deleteAllEvents() throws IOException {
        List<Event> events = eventEndpoint.getAll().execute().getItems();
        if(events != null) {
            for (Event event: events){
                eventEndpoint.delete(event.getId()).execute();
            }
        }
        System.out.println("Deleted all events");
    }
}
