package com.eminyilmazz.smoketracker.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum Activity {
    WORKING("WORKING"), GAMING("GAMING"), LEISURE("LEISURE"), OTHER("OTHER");

    private String value;

    Activity(String value) {
        this.value = value;
    }

    private static final Map<String, Activity> ALIAS_MAP = new HashMap<>();
    static {
        ALIAS_MAP.put("WORKING", Activity.WORKING);
        ALIAS_MAP.put("WORK", Activity.WORKING);
        ALIAS_MAP.put("GAMING", Activity.GAMING);
        ALIAS_MAP.put("GAME", Activity.GAMING);
        ALIAS_MAP.put("LEISURE", Activity.LEISURE);
        ALIAS_MAP.put("NETFLIX", Activity.LEISURE);
        ALIAS_MAP.put("YOUTUBE", Activity.LEISURE);
        ALIAS_MAP.put("TWITCH", Activity.LEISURE);
        ALIAS_MAP.put("OTHER", Activity.OTHER);
    }

    public static Activity fromValue(String value) {
        return ALIAS_MAP.getOrDefault(value.toUpperCase(), Activity.OTHER);
    }

    public String getValue() {
        return this.value;
    }
}
