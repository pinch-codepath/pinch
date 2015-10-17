package com.pinch.console;

import com.pinch.backend.organizationEndpoint.OrganizationEndpoint;
import com.pinch.backend.organizationEndpoint.model.Organization;

import java.io.IOException;
import java.util.List;

public class DeleteAllOrganizations{

    public static void main(String[] args) throws IOException {
        OrganizationEndpoint endpoint = Endpoints.getInstance().organizationEndpoint;
        List<Organization> organizations = endpoint.getAll().execute().getItems();
        if(organizations != null) {
            for (Organization organization: organizations){
                endpoint.delete(organization.getId()).execute();
            }
        }
    }
}
