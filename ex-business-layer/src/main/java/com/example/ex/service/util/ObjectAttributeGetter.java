package com.example.ex.service.util;

import org.json.JSONObject;

public class ObjectAttributeGetter extends AbstractJSONAttributeGetter<String> {

    @Override
    public String getAttribute(JSONObject json, String attribute) {
        return json.get(attribute).toString();
    }

}
