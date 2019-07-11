package com.example.ex.service.util;

import org.json.JSONObject;

public interface JSONAttributeGetter<T> {

    public T apply(JSONObject json, String attribute);
}
