package com.pinch.console;

import com.pinch.backend.userEndpoint.model.User;

import java.io.IOException;

public class UserLifecycle {
    public static void main(String[] args) throws IOException {
        User user = new User();
        user.setName("TestUser");
        user.setAuthSource("10");
        user.setAuthId("10");
        User updatedUser = Endpoints.getInstance().userEndpoint.insertIfMissing(user).execute();
        System.out.println(updatedUser.getId() + "");
        User queriedUser = Endpoints.getInstance().userEndpoint.get(updatedUser.getId()).execute();
        System.out.println(queriedUser.getName());
        Endpoints.getInstance().userEndpoint.delete(updatedUser.getId());
        System.out.println("User Deleted!");
    }
}
