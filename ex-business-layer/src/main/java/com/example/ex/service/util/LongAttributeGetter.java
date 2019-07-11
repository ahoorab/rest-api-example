package com.example.ex.service.util;

import org.json.JSONObject;

public class LongAttributeGetter extends AbstractJSONAttributeGetter<Long> {

    @Override
    public Long getAttribute(JSONObject json, String attribute) {
        return json.getLong(attribute);
    }

}
