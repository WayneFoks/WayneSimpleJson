package com.wayne.json;

import com.wayne.json.annotation.BasicClass;
import com.wayne.json.annotation.JsonField;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class SimpleJson {

    /**
     * java对象转成json对象
     *
     * @param object java对象
     * @return json对象
     */
    public static JSONObject toJson(Object object) {
        Class c = object.getClass();
        JSONObject jsonObject = new JSONObject();
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
                    elementClass = ((JsonField) annotation).clazz();
                    break;
                }
            }
            if (isEmpty(key)) {
                continue;
            }

            field.setAccessible(true);
            try {
                if (Collection.class.isAssignableFrom(field.getType())) {
                    doCollection2Json(object, jsonObject, field, key, elementClass);
                } else {
                    jsonObject.put(key, toJson(elementClass, field.get(object)));
                }
            } catch (JSONException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    /**
     * json对象转java对象
     *
     * @param pojo       java对象
     * @param jsonObject json对象
     */
    public static void parseJson(Object pojo, JSONObject jsonObject) {
        Class c = pojo.getClass();
        for (Field field : c.getDeclaredFields()) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            if (annotations == null) {
                continue;
            }
            String key = "";
            Class clazz = null;
            for (Annotation annotation : annotations) {
                if (annotation instanceof JsonField) {
                    key = ((JsonField) annotation).name();
                    clazz = ((JsonField) annotation).clazz();
                    break;
                }
            }
            if (isEmpty(key) || !jsonObject.has(key)) {
                continue;
            }
            field.setAccessible(true);

            try {
                Object jsonValue = jsonObject.get(key);
                if (jsonValue instanceof JSONArray && Collection.class.isAssignableFrom(field.getType())) {
                    doJson2Collection(pojo, field, clazz, (JSONArray) jsonValue);
                } else {
                    field.set(pojo, parseJsonByClass(clazz, jsonValue));
                }
            } catch (JSONException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 转换一个json为java对象（BasicClass.class or not）或者普通值类型
     */
    public static Object parseJsonByClass(Class clazz, Object object) {
        if (clazz != BasicClass.class) {
            JSONObject jsonObject = (JSONObject) object;
            Object classObject = null;
            try {
                classObject = clazz.newInstance();
                parseJson(classObject, jsonObject);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return classObject;
        } else {
            return object;
        }
    }

    private static Object toJson(Class clazz, Object fieldObject) {
        if (clazz != BasicClass.class && fieldObject != null) {
            return toJson(fieldObject);
        } else {
            return fieldObject;
        }
    }

    /**
     * 转化json为一个Collection对象
     *
     * @param pojo      实例类
     * @param field     Collection
     * @param clazz     Collection的元素类型
     * @param jsonArray jsonArray
     */
    @SuppressWarnings("unchecked")
    private static void doJson2Collection(Object pojo, Field field, Class clazz, JSONArray jsonArray) {
        try {
            Collection arrayFieldObject = doCollectionInstance(pojo, field);
            for (int i = 0; i < jsonArray.length(); i++) {
                Object elementObject = parseJsonByClass(clazz, jsonArray.get(i));
                arrayFieldObject.add(elementObject);
            }
            field.set(pojo, arrayFieldObject);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 转化Collection为一个json对象
     */
    private static void doCollection2Json(Object object, JSONObject jsonObject, Field field, String key, Class elementClass) {
        JSONArray jsonArray = new JSONArray();
        Collection collection = null;
        try {
            collection = (Collection) field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (collection != null) {
            for (Object aCollection : collection) {
                jsonArray.put(toJson(elementClass, aCollection));
            }
            jsonObject.put(key, jsonArray);
        }
    }

    /**
     * 创建pojo对应成员变量的Collection实例，如果为空；否则返回原变量
     * 如果是接口，默认ArrayList
     *
     * @param pojo  对象
     * @param field 变量
     * @return Collection实例
     */
    private static Collection doCollectionInstance(Object pojo, Field field) throws IllegalAccessException, InstantiationException {
        Object fieldObject = field.get(pojo);
        if (fieldObject == null) {
            if (field.getType().isInterface()) {
                fieldObject = new ArrayList<>();
            } else {
                fieldObject = field.getType().newInstance();
            }
            field.set(pojo, fieldObject);
        }
        return (Collection) fieldObject;
    }

    private static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

}
