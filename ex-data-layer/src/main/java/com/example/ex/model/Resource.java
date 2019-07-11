package com.example.ex.model;

import lombok.Getter;

@Getter
public enum Resource {
    
    APP_INSTANCE("appinstances"),
    APP_JVM("appjvms"),
    APP_SERVER("appservers"), 
    APP_TYPE("apptypes"), 
    BLOCKED_COUNTER_PARTY("blockedcounterparties"),
    BOOK("book"),
    CREDIT_DASHBOARD("creditmatrix"),
    CREDIT_LINE("creditlines"),
    CREDIT_POOL("creditpools"),
    CURRENCY("currencies"),
    CURRENCY_PAIR("currencypairs"),
    CURRENCY_PAIR_SETTLE_DATE("ccpairsettledates"),
    DEAL_CODE("dealcodes"),
    ERROR("error"),
    FIRM("firms"),
    FIX_SESSION("fixsessions"),
    FIX_SESSION_MPID("fixsessionmpids"),
    HOLIDAY("holidays"),
    LIVE("live"),
    MPID("mpids"),
    REF_DATA("refdata"),
    SCHEDULE_EVENT("scheduleevents"),
    SWAGGER("swagger-ui.html"),
    SWAGGER_WEBJARS("webjars"),
    SWAGGER_RESOURCES("swagger-resources"),
    SYSTEM_STATE("systemstates"),
    TRADE_NOTIFICATION_RULE("tradenotificationrules"),
    USER("users"),
    VENUE("venues"),
    WEBSOCKETS_DATA("data"),
    WEBSOCKETS_TEST("websockets");

    public final String uri;
    private Resource parent;
    
    private Resource(String uri) {
        this(uri,null);
    }
    private Resource(String uri, Resource parent) {
        this.uri = uri;
        this.parent = parent;
    }
    
    public static Resource fromUri(String uri) {
        for (Resource resource : Resource.values()) {
            if (resource.getUri().equalsIgnoreCase(uri)) {
                return resource;
            }
        }
        return null;
    }

}
