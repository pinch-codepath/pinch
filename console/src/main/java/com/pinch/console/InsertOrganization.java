package com.pinch.console;

import com.pinch.backend.organizationEndpoint.model.Organization;

import java.io.IOException;

public class InsertOrganization {
    public static void main(String[] args) throws IOException {
        Organization organization = new Organization();
        organization.setName("HelloWorld");
        Organization returnedOrg = Endpoints.getInstance().organizationEndpoint.insert(organization).execute();
        if(returnedOrg != null) {
            System.out.println(returnedOrg.getId());
        }
    }
}
