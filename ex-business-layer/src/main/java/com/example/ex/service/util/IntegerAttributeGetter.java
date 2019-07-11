package com.example.ex.service.util;

import org.json.JSONObject;

public class IntegerAttributeGetter extends AbstractJSONAttributeGetter<Integer> {

    @Override
    public Integer getAttribute(JSONObject json, String attribute) {
        return json.getInt(attribute);
    }

}
