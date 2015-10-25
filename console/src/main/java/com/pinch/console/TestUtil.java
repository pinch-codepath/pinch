package com.pinch.console;

import com.google.api.client.util.DateTime;

import com.pinch.backend.eventEndpoint.model.Event;
import com.pinch.backend.organizationEndpoint.model.GeoPt;
import com.pinch.backend.organizationEndpoint.model.Organization;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TestUtil {

    static Long insertOrg(String name, String address, float latitude, float longitude, String url, String displayUrl) throws IOException {
        GeoPt geoPt = new GeoPt();
        geoPt.setLatitude(latitude);
        geoPt.setLongitude(longitude);
        Organization organization = new Organization();
        organization.setName(name);
        organization.setAddress(address);
        organization.setLocation(geoPt);
        organization.setUrl(url);
        organization.setDisplayUrl(displayUrl);
        Organization returnedOrg = Endpoints.getInstance().organizationEndpoint.insert(organization).execute();
        if(returnedOrg != null) {
            System.out.println("Inserted organization: " + returnedOrg);
        }
        return returnedOrg.getId();
    }

    static Long insertEvent(long orgId1, String title, String description, String startTime, String endTime, Address address, Skills skills) throws IOException, ParseException {
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
        event.setSkill1(skills.getSkill1());
        event.setSkill2(skills.getSkill2());
        event.setSkill3(skills.getSkill3());
        Event returnedEvent = Endpoints.getInstance().eventEndpoint.insert(orgId1, event).execute();
        if (returnedEvent != null) {
            System.out.println("Inserted event: " + returnedEvent);
        }
        return returnedEvent.getId();
    }

    // Can later be made into a composite object within events and organization.
    static class Address {

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

    static DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    // Can later be made into a composite object within events.
    static class Skills {
        private String skill1;
        private String skill2;
        private String skill3;

        public String getSkill1() {
            return skill1;
        }

        public void setSkill1(String skill1) {
            this.skill1 = skill1;
        }

        public String getSkill2() {
            return skill2;
        }

        public void setSkill2(String skill2) {
            this.skill2 = skill2;
        }

        public String getSkill3() {
            return skill3;
        }

        public void setSkill3(String skill3) {
            this.skill3 = skill3;
        }

        Skills(String skill1, String skill2, String skill3) {
            this.skill1 = skill1;
            this.skill2 = skill2;
            this.skill3 = skill3;
        }
    }

    static void deleteAllOrgs() throws IOException {
        List<Organization> organizations = Endpoints.getInstance().organizationEndpoint.getAll().execute().getItems();
        if(organizations != null) {
            for (Organization organization: organizations){
                Endpoints.getInstance().organizationEndpoint.delete(organization.getId()).execute();
            }
        }
        System.out.println("Deleted all orgs");
    }

    static void deleteAllEvents() throws IOException {
        List<Event> events = Endpoints.getInstance().eventEndpoint.getAll().execute().getItems();
        if(events != null) {
            for (Event event: events){
                Endpoints.getInstance().eventEndpoint.delete(event.getId()).execute();
            }
        }
        System.out.println("Deleted all events");
    }

}
