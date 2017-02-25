package com.company;

import com.wayne.json.SimpleJson;
import com.wayne.json.utils.SimpleJsonUtils;

public class Main {

    public static void main(String[] args) {
        // write your code here
        SubscribeItem subscribeItem = new SubscribeItem();
        SimpleJsonUtils.fromJSON(subscribeItem, Mocker.B);
        String jsonString = SimpleJson.toJson(subscribeItem).toString();
        System.out.print(jsonString);
    }
}
