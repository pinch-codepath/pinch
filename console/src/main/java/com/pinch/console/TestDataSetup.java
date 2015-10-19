package com.pinch.console;

import com.google.api.client.util.DateTime;

import com.pinch.backend.eventEndpoint.EventEndpoint;
import com.pinch.backend.eventEndpoint.model.Event;
import com.pinch.backend.organizationEndpoint.OrganizationEndpoint;
import com.pinch.backend.organizationEndpoint.model.GeoPt;
import com.pinch.backend.organizationEndpoint.model.Organization;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TestDataSetup {

    private static OrganizationEndpoint organizationEndpoint = Endpoints.getInstance().organizationEndpoint;
    private static EventEndpoint eventEndpoint = Endpoints.getInstance().eventEndpoint;

    public static void main(String[] args) throws IOException, ParseException {
        deleteAllEvents();

        deleteAllOrgs();

        long orgId1 = insertOrg("Curry Senior Center", "333 Turk Street San Francisco, CA 94102", 37.782582f, -122.414442f, "http://curryseniorcenter.org/how-to-help/volunteer/");
        long orgId2 = insertOrg("Next Door Shelter", "Episcopal Community Services 165 8th Street, 3rd Floor San Francisco, CA 94103", 37.776814f, -122.412128f, "http://ecs-sf.org/getinvolved/volunteer.html");
        long orgId3 = insertOrg("Sanctuary Center", "Episcopal Community Services 165 8th Street, 3rd Floor San Francisco, CA 94103", 37.776814f, -122.412128f, "http://ecs-sf.org/getinvolved/volunteer.html");
        long orgId4 = insertOrg("Glide Memorial", "330 Ellis Street, San Francisco, CA 94102", 37.785268f, -122.411432f, "http://www.glide.org/serveameal");
        long orgId5 = insertOrg("SF Marin Food Bank", "900 Pennsylvania Avenue San Francisco, CA 94107", 37.754461f, -122.393679f, "http://www.sfmfoodbank.org/volunteer-opportunities");
        long orgId6 = insertOrg("Micro Mentor", "Mercy Corps 45 SW Ankeny Street Portland, OR 97204", 45.522617f, -122.670751f, "http://www.micromentor.org/");

        Address a1 = new Address ("550 Polk St", "San Francisco", "CA", 94102, "Civic Center");
        Address a2 = new Address ("155 9th st", "San Francisco", "CA", 94103, "SOMA");
        Address a3 = new Address ("201 8th Street", "San Francisco", "CA", 94103, "SOMA");
        Address a4 = new Address ("480 Ellis St", "San Francisco", "CA", 94102, "Tenderloin");


        insertEvent(orgId1,
                "Serve Breakfast To The Elderly",
                "One to five volunteers are needed each day to assist kitchen staff in serving breakfast and lunch. The breakfast hours are " +
                        "6:30 am to 9:00 am. The lunch hours are 10:00 am to 1:30 pm. You may choose to do either one or both shifts on the " +
                        "day of your choice.",
                "11/19/2015 06:30:00",
                "11/19/2015 09:00:00",
                a1);

        insertEvent(orgId3,
                "Community Cleanup",
                "Join us for a huge Community Cleanup Day in West Oakland. Projects may include: Garden planting, Park renovation, Painting, " +
                        "Graffiti removal, Litter removal, Weeding, Illegal dumping collection, Breakfast snacks and liquids will be provided. " +
                        "We recommend bringing your own refillable bottle.",
                "11/19/2015 13:00:00",
                "11/19/2015 16:00:00",
                a2);

        insertEvent(orgId2,
                "Serve Breakfast To The Elderly",
                "Volunteering is easy and fun at Episcopal Community Services! We have lots of opportunities for those who want to make a difference in the lives of homeless and low-income adults, seniors and youth.",
                "11/19/2015 07:00:00",
                "11/19/2015 09:00:00",
                a3);

        insertEvent(orgId3,
                "Serve Breakfast",
                "Volunteering is easy and fun at Episcopal Community Services! We have lots of opportunities for those who want to make a difference in the lives of homeless and low-income adults, seniors and youth.",
                "11/19/2015 07:00:00",
                "11/19/2015 09:00:00",
                a1);

        insertEvent(orgId4,
                "Help setup event for school",
                "Join other volunteers and help K to College prepare for their 5th Annual Assembly Event. Set-up will take place at the International House in the Chevron Auditorium. We will set-up tables, chairs, signage, and boxes in preparation for the next day where volunteers will assemble 3,500 school supply kits and dental kits that will be given to every child eligible for the free & reduced lunch plan.",
                "10/19/2015 07:00:00",
                "10/19/2015 09:00:00",
                a1);

        insertEvent(orgId5,
                "Warehouse Volunteer",
                "Every day of the week, teams of volunteers gather to sort and pack the food donations that come through our doors. From bins of oranges straight from the grove to 2,000-pound totes of rice, volunteers ensure that there are always hands at the ready to pack the food for distribution. Last year, volunteers provided 146,000 hours of help — the equivalent of 70 full time staff and a critical support toward ending hunger.",
                "10/19/2015 09:00:00",
                "10/19/2015 12:00:00",
                a2);

        insertEvent(orgId4,
                "Serve Lunch",
                "Serving a meal in the Daily Free Meals Program can be a transformative experience. This program requires 85 volunteers each day to fill the breakfast, prep, lunch and dinner shifts, 364 days a year. With your help we can serve up to 2400 meals per day to our community. Volunteers assist with everything from serving food and bussing tables, to handing out silverware and condiments. Be prepared to roll up your sleeves, break a sweat, and make some beautiful human connections!",
                "11/19/2015 11:00:00",
                "11/19/2015 14:00:00",
                a3);

        insertEvent(orgId5,
                "Warehouse Volunteer",
                "Every day of the week, teams of volunteers gather to sort and pack the food donations that come through our doors. From bins of oranges straight from the grove to 2,000-pound totes of rice, volunteers ensure that there are always hands at the ready to pack the food for distribution. Last year, volunteers provided 146,000 hours of help — the equivalent of 70 full time staff and a critical support toward ending hunger.",
                "11/20/2015 12:30:00",
                "11/20/2015 15:30:00",
                a4);

        insertEvent(orgId5,
                "Warehouse Volunteer",
                "Every day of the week, teams of volunteers gather to sort and pack the food donations that come through our doors. From bins of oranges straight from the grove to 2,000-pound totes of rice, volunteers ensure that there are always hands at the ready to pack the food for distribution. Last year, volunteers provided 146,000 hours of help — the equivalent of 70 full time staff and a critical support toward ending hunger.",
                "11/20/2015 14:00:00",
                "11/20/2015 16:00:00",
                a1);

        insertEvent(orgId4,
                "Serve Dinner",
                "Serving a meal in the Daily Free Meals Program can be a transformative experience. This program requires 85 volunteers each day to fill the breakfast, prep, lunch and dinner shifts, 364 days a year. With your help we can serve up to 2400 meals per day to our community. Volunteers assist with everything from serving food and bussing tables, to handing out silverware and condiments. Be prepared to roll up your sleeves, break a sweat, and make some beautiful human connections!",
                "10/20/2015 15:00:00",
                "10/20/2015 17:30:00",
                a2);

        insertEvent(orgId2,
                "Serve Dinner",
                "Volunteering is easy and fun at Episcopal Community Services! We have lots of opportunities for those who want to make a difference in the lives of homeless and low-income adults, seniors and youth.",
                "11/20/2015 16:30:00",
                "11/20/2015 18:00:00",
                a1);

        insertEvent(orgId3,
                "Serve Dinner",
                "Volunteering is easy and fun at Episcopal Community Services! We have lots of opportunities for those who want to make a difference in the lives of homeless and low-income adults, seniors and youth.",
                "11/21/2015 16:30:00",
                "11/21/2015 18:00:00",
                a3);

        insertEvent(orgId5,
                "Warehouse Volunteer",
                "Every day of the week, teams of volunteers gather to sort and pack the food donations that come through our doors. From bins of oranges straight from the grove to 2,000-pound totes of rice, volunteers ensure that there are always hands at the ready to pack the food for distribution. Last year, volunteers provided 146,000 hours of help — the equivalent of 70 full time staff and a critical support toward ending hunger.",
                "11/21/2015 18:00:00",
                "11/21/2015 20:00:00",
                a1);

        insertEvent(orgId4,
                "Serve Dinner",
                "Serving a meal in the Daily Free Meals Program can be a transformative experience. This program requires 85 volunteers each day to fill the breakfast, prep, lunch and dinner shifts, 364 days a year. With your help we can serve up to 2400 meals per day to our community. Volunteers assist with everything from serving food and bussing tables, to handing out silverware and condiments. Be prepared to roll up your sleeves, break a sweat, and make some beautiful human connections!",
                "10/21/2015 15:00:00",
                "10/21/2015 17:30:00",
                a2);

        insertEvent(orgId2,
                "Serve Dinner",
                "Volunteering is easy and fun at Episcopal Community Services! We have lots of opportunities for those who want to make a difference in the lives of homeless and low-income adults, seniors and youth.",
                "11/21/2015 16:30:00",
                "11/21/2015 18:00:00",
                a1);

        insertEvent(orgId6,
                "Serve Dinner",
                "Volunteering is easy and fun at Episcopal Community Services! We have lots of opportunities for those who want to make a difference in the lives of homeless and low-income adults, seniors and youth.",
                "11/21/2015 16:30:00",
                "11/21/2015 18:00:00",
                a4);

        insertEvent(orgId5,
                "Warehouse Volunteer",
                "Every day of the week, teams of volunteers gather to sort and pack the food donations that come through our doors. From bins of oranges straight from the grove to 2,000-pound totes of rice, volunteers ensure that there are always hands at the ready to pack the food for distribution. Last year, volunteers provided 146,000 hours of help — the equivalent of 70 full time staff and a critical support toward ending hunger.",
                "11/22/2015 18:00:00",
                "11/22/2015 20:00:00",
                a3);
    }

    private static DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    private static void insertEvent(long orgId1, String title, String description, String startTime, String endTime, Address address) throws IOException, ParseException {
        Event event = new Event();
        event.setTitle(title);
        event.setDescription(description);
        event.setStartTime(new DateTime(formatter.parse(startTime)));
        event.setEndTime(new DateTime(formatter.parse(endTime)));
        event.setAddressStreet(address.getStreet());
        event.setAddressCity(address.getCity());
        event.setAddressState(address.getState());
        event.setAddressZip(address.getZip());
        event.setAddressNeighborhood(address.getNeighborhood());
        Event returnedEvent = eventEndpoint.insert(orgId1, event).execute();
        if(returnedEvent != null) {
            System.out.println("Inserted event: " + returnedEvent);
        }
    }

    private static Long insertOrg(String name, String address, float latitude, float longitude, String url) throws IOException {
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
        return returnedOrg.getId();
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
                eventEndpoint.delete(event.getOrganization().getId(), event.getId()).execute();
            }
        }
        System.out.println("Deleted all events");
    }

    // Can later be made into a composite object within events and organization.
    public static class Address {

        private long zip;
        private String street;
        private String city;
        private String state;
        private String neighborhood;

        public long getZip() {
            return zip;
        }

        public void setZip(long zip) {
            this.zip = zip;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getNeighborhood() {
            return neighborhood;
        }

        public void setNeighborhood(String neighborhood) {
            this.neighborhood = neighborhood;
        }

        Address(String street, String city, String state, long zip, String neighborhood) {
            this.street = street;
            this.city = city;
            this.state = state;
            this.zip = zip;
            this.neighborhood = neighborhood;
        }

    }

}
