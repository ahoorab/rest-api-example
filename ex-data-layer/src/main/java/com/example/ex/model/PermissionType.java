package com.example.ex.model;

import org.springframework.http.HttpMethod;

import lombok.Getter;

@Getter
public enum PermissionType {
    
    CREATE(HttpMethod.POST), READ(HttpMethod.GET), UPDATE(HttpMethod.PUT), DELETE(HttpMethod.DELETE), ALL(null);
        
    private HttpMethod method;
        
    PermissionType(HttpMethod method) {
        this.method = method;
    }

    public static PermissionType fromHttpMethod(HttpMethod method) {
        if (method != null) {
            for (PermissionType type : PermissionType.values()) {
                if (type.method.equals(method)) {
                    return type;
                }
           }
        }
        return null;
    }

}
