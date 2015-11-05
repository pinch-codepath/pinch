package com.pinch.console;

import com.pinch.backend.affiliationEndpoint.model.Affiliation;
import com.pinch.backend.userEndpoint.model.Organization;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class AffiliateLifecycle {
    public static void main(String[] args) throws IOException, ParseException {
//        User user = new User();
//        user.setName("TestUser");
//        user.setAuthSource("facebook");
//        user.setAuthId("10");
//        User updatedUser = Endpoints.getInstance().userEndpoint.insertIfMissing(user).execute();
//        System.out.println(updatedUser.getId() + "");
//
//        long orgId1 = TestUtil.insertOrg("Curry Senior Center", "333 Turk Street San Francisco, CA 94102", 37.782582f, -122.414442f, "", "http://curryseniorcenter.org/how-to-help/volunteer/");

        Affiliation affiliation = new Affiliation();
//        affiliation.setUserId(6531887195488256L);
//        affiliation.setOrganizationId(orgId1);
//        affiliation = Endpoints.getInstance().affiliationEndpoint.register(affiliation).execute();
       //List<Organization> organizations = Endpoints.getInstance().userEndpoint.getAffiliationsForUser(affiliation.getUserId()).execute().getItems();
//        if(organizations != null) {
//            for (Organization org: organizations){
//                System.out.println(org);
//            }
//        }

        affiliation.setUserId(5855512792924160L);
        affiliation.setOrganizationId(5825700351180800L);
        affiliation = Endpoints.getInstance().affiliationEndpoint.register(affiliation).execute();
        List<Organization> organizations = Endpoints.getInstance().userEndpoint.getAffiliationsForUser(affiliation.getUserId()).execute().getItems();
        if(organizations != null) {
            for (Organization org: organizations){
                System.out.println(org);
            }
        }

        affiliation.setUserId(4649932115935232L);
        affiliation.setOrganizationId(4665846311944192L);
        affiliation = Endpoints.getInstance().affiliationEndpoint.register(affiliation).execute();
        organizations = Endpoints.getInstance().userEndpoint.getAffiliationsForUser(affiliation.getUserId()).execute().getItems();
        if(organizations != null) {
            for (Organization org: organizations){
                System.out.println(org);
            }
        }
//        Endpoints.getInstance().affiliationEndpoint.unregister(affiliation.getId()).execute();
//        System.out.println("Deleted");
//        organizations = Endpoints.getInstance().userEndpoint.getAffiliationsForUser(affiliation.getUserId()).execute().getItems();
//        if(organizations != null) {
//            for (Organization org: organizations){
//                System.out.println(org);
//            }
//        }
    }
}
