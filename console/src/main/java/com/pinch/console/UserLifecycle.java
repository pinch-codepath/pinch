package com.pinch.console;

import com.pinch.backend.userEndpoint.model.User;

import java.io.IOException;

public class UserLifecycle {
    public static void main(String[] args) throws IOException {
        User user = new User();
        user.setName("TestUser");
        user.setId("10");
        User updatedUser = Endpoints.getInstance().userEndpoint.insertIfMissing(user).execute();
        System.out.println(updatedUser.getKey() + "");
        User queriedUser = Endpoints.getInstance().userEndpoint.get(updatedUser.getKey()).execute();
        System.out.println(queriedUser.getName());
        Endpoints.getInstance().userEndpoint.delete(updatedUser.getKey());
        System.out.println("User Deleted!");
    }
}
