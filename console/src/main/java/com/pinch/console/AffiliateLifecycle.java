package com.pinch.console;

import com.pinch.backend.affiliationEndpoint.model.Affiliation;
import com.pinch.backend.userEndpoint.model.Organization;
import com.pinch.backend.userEndpoint.model.User;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class AffiliateLifecycle {
    public static void main(String[] args) throws IOException, ParseException {
        User user = new User();
        user.setName("TestUser");
        user.setId("10");
        User updatedUser = Endpoints.getInstance().userEndpoint.insertIfMissing(user).execute();
        System.out.println(updatedUser.getKey() + "");

        long orgId1 = TestUtil.insertOrg("Curry Senior Center", "333 Turk Street San Francisco, CA 94102", 37.782582f, -122.414442f, "", "http://curryseniorcenter.org/how-to-help/volunteer/");

        Affiliation affiliation = new Affiliation();
        affiliation.setUserId(updatedUser.getKey());
        affiliation.setOrganizationId(orgId1);
        affiliation = Endpoints.getInstance().affiliationEndpoint.register(affiliation).execute();
        List<Organization> organizations = Endpoints.getInstance().userEndpoint.getAffiliationsForUser(affiliation.getUserId()).execute().getItems();
        if(organizations != null) {
            for (Organization org: organizations){
                System.out.println(org);
            }
        }
        Endpoints.getInstance().affiliationEndpoint.unregister(affiliation.getId()).execute();
        System.out.println("Deleted");
        organizations = Endpoints.getInstance().userEndpoint.getAffiliationsForUser(affiliation.getUserId()).execute().getItems();
        if(organizations != null) {
            for (Organization org: organizations){
                System.out.println(org);
            }
        }
    }
}
