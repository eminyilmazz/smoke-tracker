package com.eminyilmazz.smoketracker.enums;

import java.util.HashMap;
import java.util.Map;

public enum Activity {
    WORKING("working"), GAMING("gaming"), LEISURE("leisure"), OTHER("other");

    private String value;

    Activity(String value) {
        this.value = value;
    }

    private static final Map<String, Activity> ALIAS_MAP = new HashMap<>();
    static {
        ALIAS_MAP.put("working", Activity.WORKING);
        ALIAS_MAP.put("work", Activity.WORKING);
        ALIAS_MAP.put("gaming", Activity.GAMING);
        ALIAS_MAP.put("game", Activity.GAMING);
        ALIAS_MAP.put("leisure", Activity.LEISURE);
        ALIAS_MAP.put("netflix", Activity.LEISURE);
        ALIAS_MAP.put("youtube", Activity.LEISURE);
        ALIAS_MAP.put("twitch", Activity.LEISURE);
        ALIAS_MAP.put("other", Activity.OTHER);
    }

    public static Activity fromValue(String value) {
        return ALIAS_MAP.getOrDefault(value.toLowerCase(), Activity.OTHER);
    }

    public String getValue() {
        return this.value;
    }
}
