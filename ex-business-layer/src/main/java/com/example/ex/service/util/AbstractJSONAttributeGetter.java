package com.example.ex.service.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJSONAttributeGetter<T> implements JSONAttributeGetter<T> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractJSONAttributeGetter.class);
    
    public T apply(JSONObject json, String attribute) {
        if (json != null) {
            try {
                return getAttribute(json,attribute);
            } catch (JSONException e) {
                LOGGER.error("error while trying to get attribute {} from ref data", attribute, e);
            }
        } else {
            LOGGER.error("Getting attribute <{}> from null json object", attribute);
        }
        return null;
    }

    public abstract T getAttribute(JSONObject json, String attribute) ;
    
}
