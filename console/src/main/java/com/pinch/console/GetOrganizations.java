package com.pinch.console;

import com.pinch.backend.organizationEndpoint.model.Organization;

import java.io.IOException;
import java.util.List;

public class GetOrganizations {
    public static void main(String[] args) throws IOException {
        List<Organization> organizations = Endpoints.getInstance().organizationEndpoint.getAll().execute().getItems();
        if(organizations != null) {
            for (Organization organization: organizations){
                System.out.println(organization);
            }
        }
    }
}
