package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        SubscribeItem subscribeItem = new SubscribeItem();
        SimpleJsonConverter.fromJSON(subscribeItem, Mocker.A);
        String jsonString = SimpleJsonConverter.toJSON(subscribeItem).toString();
        System.out.print(jsonString);
    }
}
