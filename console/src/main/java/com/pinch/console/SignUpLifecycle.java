package com.pinch.console;

import com.pinch.backend.eventEndpoint.model.Event;
import com.pinch.backend.signUpEndpoint.model.SignUp;
import com.pinch.backend.userEndpoint.model.User;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class SignUpLifecycle {
    public static void main(String[] args) throws IOException, ParseException {
        User user = new User();
        user.setName("TestUser");
        user.setId("10");
        User updatedUser = Endpoints.getInstance().userEndpoint.insertIfMissing(user).execute();
        System.out.println(updatedUser.getKey() + "");

        long orgId1 = TestUtil.insertOrg("Curry Senior Center", "333 Turk Street San Francisco, CA 94102", 37.782582f, -122.414442f, "", "http://curryseniorcenter.org/how-to-help/volunteer/");
        TestUtil.Address a1 = new TestUtil.Address("550 Polk St", "San Francisco", "CA", 94102, "Civic Center");
        TestUtil.Skills s1 = new TestUtil.Skills("Cooking", "Cleaning", "Changing Diapers");

        long eventId = TestUtil.insertEvent(orgId1,
                "Serve Breakfast To The Elderly",
                "One to five volunteers are needed each day to assist kitchen staff in serving breakfast and lunch. The breakfast hours are " +
                        "6:30 am to 9:00 am. The lunch hours are 10:00 am to 1:30 pm. You may choose to do either one or both shifts on the " +
                        "day of your choice.",
                "10/26/2015 06:30:00",
                "10/26/2015 09:00:00",
                a1, s1, "http://lh3.googleusercontent.com/Z0FEe6qtHrX1KxNMBGRZIYr2jRmQSVxmWqf_lUvASEFrldFD0HCM5tiy_zmUl3-9VDewilgBNmmwCgFHCmHPGg");

        SignUp signUp = new SignUp();
        signUp.setUserId(updatedUser.getKey());
        signUp.setEventId(eventId);
        signUp = Endpoints.getInstance().signUpEndpoint.register(signUp).execute();
        List<Event> events = Endpoints.getInstance().eventEndpoint.getSignedUpEventsForUser(signUp.getUserId()).execute().getItems();
        if(events != null) {
            for (Event event: events){
                System.out.println(event);
            }
        }
        Endpoints.getInstance().signUpEndpoint.unregister(signUp.getId()).execute();
        System.out.println("Deleted");
        events = Endpoints.getInstance().eventEndpoint.getSignedUpEventsForUser(signUp.getUserId()).execute().getItems();
        if(events != null) {
            for (Event event: events){
                System.out.println(event);
            }
        }
    }
}
