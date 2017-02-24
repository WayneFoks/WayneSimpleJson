/*
 * Copyright (C) 2004 - 2017 UCWeb Inc. All Rights Reserved.
 * Description :
 *
 * Creation    : 2017/02/22
 * Author      : weihan.hwh@alibaba-inc.com
 */

package com.company;


import com.company.annotation.JsonField;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class SimpleJsonConverter {

    public static JSONObject toJSON(Object object) {
        Class c = object.getClass();
        JSONObject jsonObject = new JSONObject();
        for (Field field : c.getDeclaredFields()) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            if (annotations == null) {
                continue;
            }
            String key = "";
            for (Annotation annotation : annotations) {
                if (annotation instanceof JsonField) {
                    key = ((JsonField) annotation).name();
                    break;
                }
            }
            if (TextUtils.isEmpty(key)) {
                continue;
            }
            field.setAccessible(true);
            try {
                jsonObject.put(key, field.get(object));
            } catch (JSONException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public static void fromJSON(Object object, String jsonString) {
        try {
            fromJSON(object, new JSONObject(jsonString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void fromJSON(Object object, JSONObject jsonObject) {
        Class c = object.getClass();
        for (Field field : c.getDeclaredFields()) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            if (annotations == null) {
                continue;
            }
            String key = "";
            Class elementClass = null;
            for (Annotation annotation : annotations) {
                if (annotation instanceof JsonField) {
                    key = ((JsonField) annotation).name();
                    elementClass = ((JsonField) annotation).elementClass();
                    break;
                }
            }
            if (TextUtils.isEmpty(key) || !jsonObject.has(key)) {
                continue;
            }
            field.setAccessible(true);

            try {
                Object jsonValue = jsonObject.get(key);
                if (jsonValue instanceof JSONArray && Collection.class.isAssignableFrom(field.getType())) {
                    Object fieldObject = field.get(object);
                    if (fieldObject == null) {
                        if (field.getType().isInterface()) {
                            fieldObject = new ArrayList<>();
                        } else {
                            fieldObject = field.getType().newInstance();
                        }
                        field.set(object, fieldObject);
                    }
                    Collection arrayFieldObject = (Collection) fieldObject;

                    JSONArray jsonArray = (JSONArray) jsonValue;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        arrayFieldObject.add(jsonArray.get(i));
                    }
                    field.set(object, arrayFieldObject);
                } else {
                    field.set(object, jsonValue);
                }
            } catch (JSONException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
