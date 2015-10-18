package com.pinch.android;

import com.google.api.client.util.DateTime;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    private static final Map<String, String> cityAbbreviations;

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

}
