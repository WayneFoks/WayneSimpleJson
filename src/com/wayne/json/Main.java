package com.wayne.json;

import com.google.gson.Gson;
import com.wayne.json.test.Mocker;
import com.wayne.json.test.SubscribeItem;
import com.wayne.json.utils.SimpleJsonUtils;

public class Main {

    public static void main(String[] args) {
        String input = Mocker.A;

        long time = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            SubscribeItem subscribeItem = new SubscribeItem();
            SimpleJsonUtils.parseJson(subscribeItem, input);
            String jsonString = SimpleJson.toJson(subscribeItem).toString();
//            System.out.println(jsonString);
        }
        long finalTime = (System.nanoTime() - time) / 1000000;
        System.out.println("time: " + finalTime);

        time = System.nanoTime();
        for (int i = 0; i < 10000; i++) {
            Gson gson = new Gson();
            SubscribeItem subscribeItem = gson.fromJson(input, SubscribeItem.class);
            String jsonString = gson.toJson(subscribeItem);
//            System.out.println(jsonString);
        }
        finalTime = (System.nanoTime() - time) / 1000000;
        System.out.println("time: " + finalTime);
    }
}
