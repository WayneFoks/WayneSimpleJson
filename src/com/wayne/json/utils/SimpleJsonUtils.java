package com.wayne.json.utils;

import com.wayne.json.SimpleJson;
import org.json.JSONException;
import org.json.JSONObject;

public class SimpleJsonUtils {

    public static void fromJSON(Object pojo, String jsonString) {
        try {
            SimpleJson.parseJson(pojo, new JSONObject(jsonString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
