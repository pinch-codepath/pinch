package com.pinch.android;

import com.google.api.client.util.DateTime;
import com.pinch.backend.eventEndpoint.model.Organization;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static final Map<String, String> cityAbbreviations;

    private static String months[] = {
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "June",
            "July",
            "Aug",
            "Sept",
            "Oct",
            "Nov",
            "Dec"};

    private static String days[] = {
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"};

    private static Organization myOrganization;

    static {
        Map<String, String> cMap = new HashMap<String, String>();
        cMap.put("San Francisco", "SF");
        cMap.put("Los Angeles", "LA");
        cMap.put("New York", "NY");
        cMap.put("Portland", "POR");
        cityAbbreviations = Collections.unmodifiableMap(cMap);
    }

    public static String getCityShort(String city) {
        String cityShort = cityAbbreviations.get(city);
        return cityShort == null ? city : cityShort;
    }

    public static String getTimeString(DateTime dateTime) {
         SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
         return sdf.format(dateTime.getValue());
    }

    public static String getDateString(DateTime dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        return sdf.format(dateTime.getValue());
    }

    public static String getMonth(int month) {
        return months[month];
    }

    public static String getDay(int day) {
        return days[day - 1];
    }

    public static Organization getMyOrganization() {
        return myOrganization;
    }

    public static void setMyOrganization(Organization myOrg) {
        myOrganization = myOrg;
    }
}
