package com.example.ex.model;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.ex.model.type.DcType;

public enum Role {

    TW(Stream.of(new SimpleEntry<>(Resource.APP_INSTANCE, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.APP_JVM, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.APP_SERVER, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.APP_TYPE, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.BLOCKED_COUNTER_PARTY, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.CREDIT_LINE, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.CREDIT_POOL, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.CURRENCY, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.CURRENCY_PAIR, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.CURRENCY_PAIR_SETTLE_DATE, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.DEAL_CODE, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.FIRM, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.FIX_SESSION, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.FIX_SESSION_MPID, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.HOLIDAY, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.MPID, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.SCHEDULE_EVENT, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.SWAGGER, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.SWAGGER_WEBJARS, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.SWAGGER_RESOURCES, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.SYSTEM_STATE, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.TRADE_NOTIFICATION_RULE, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.USER, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.VENUE, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.WEBSOCKETS_TEST, Arrays.asList(PermissionType.READ)))),
    CP(Stream.of(new SimpleEntry<>(Resource.CREDIT_LINE, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.CREDIT_POOL, Arrays.asList(PermissionType.ALL)),
                 new SimpleEntry<>(Resource.CURRENCY_PAIR, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.DEAL_CODE, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.FIRM, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.FIX_SESSION, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.FIX_SESSION_MPID, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.MPID, Arrays.asList(PermissionType.READ)),
                 //swagger permissions should be removed, this is for testing purposes
                 new SimpleEntry<>(Resource.SWAGGER, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.SWAGGER_WEBJARS, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.SWAGGER_RESOURCES, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.TRADE_NOTIFICATION_RULE, Arrays.asList(PermissionType.READ)))),
    TP(Stream.of(new SimpleEntry<>(Resource.CURRENCY_PAIR, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.DEAL_CODE, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.FIRM, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.FIX_SESSION, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.FIX_SESSION_MPID, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.MPID, Arrays.asList(PermissionType.READ)),
                 //swagger permissions should be removed, this is for testing purposes
                 new SimpleEntry<>(Resource.SWAGGER, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.SWAGGER_WEBJARS, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.SWAGGER_RESOURCES, Arrays.asList(PermissionType.READ)),
                 new SimpleEntry<>(Resource.TRADE_NOTIFICATION_RULE, Arrays.asList(PermissionType.READ))));
    
    private Role(Stream<SimpleEntry<Resource, List<PermissionType>>> permissions) {
        Map<Resource,List<PermissionType>> p = permissions.collect(Collectors.toMap(SimpleEntry::getKey,SimpleEntry::getValue));
        p.put(Resource.BOOK, Arrays.asList(PermissionType.READ));
        p.put(Resource.CREDIT_DASHBOARD, Arrays.asList(PermissionType.READ));
        p.put(Resource.ERROR, Arrays.asList(PermissionType.READ));
        p.put(Resource.LIVE, Arrays.asList(PermissionType.READ));
        p.put(Resource.REF_DATA, Arrays.asList(PermissionType.READ));
        p.put(Resource.WEBSOCKETS_DATA, Arrays.asList(PermissionType.READ));
        this.permissions = Collections.unmodifiableMap(p);
    }

    private Map<Resource,List<PermissionType>> permissions;
    
    public boolean hasPermission(Resource resource, PermissionType permission) {
        List<PermissionType> list = this.permissions.get(resource);
        if (list == null) {
            return false;
        }
        if (list.contains(PermissionType.ALL)) {
            return true;
        }
        return list.contains(permission);
    }
    
    //TODO check how to apply this rule
    public static Role fromDealCode(DcType dcType) {
        if (DcType.EXX.equals(dcType)) { 
            return Role.TW;
        }
        if (DcType.PRINCIPAL.equals(dcType)) { 
            return Role.CP;
        }
        return Role.TP;
    }
    
    //TODO check how to apply this rule
    public static Role fromGroup(String group) {
        return Role.TW;
    }
    
}