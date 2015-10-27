package com.pinch.console;

import com.pinch.backend.organizationEndpoint.model.Organization;

import java.io.IOException;

public class OrganizationLifeCycle {
    public static void main(String[] args) throws IOException {
        Organization organization = new Organization();
        organization.setName("HelloWorld");
        Organization returnedOrg = Endpoints.getInstance().organizationEndpoint.insert(organization).execute();
        if(returnedOrg != null) {
            System.out.println(returnedOrg.getId());
        }
        Organization lookedUpOrg = Endpoints.getInstance().organizationEndpoint.get(returnedOrg.getId()).execute();
        if(lookedUpOrg != null) {
            System.out.println(lookedUpOrg);
        }
        Endpoints.getInstance().organizationEndpoint.delete(returnedOrg.getId()).execute();
        lookedUpOrg = Endpoints.getInstance().organizationEndpoint.get(returnedOrg.getId()).execute();
        if(lookedUpOrg != null) {
            System.out.println(lookedUpOrg);
        }
    }
}
