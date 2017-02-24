package com.company;

public class Main {

    public static void main(String[] args) {
        // write your code here
        SubscribeItem subscribeItem = new SubscribeItem();
        SimpleJsonConverter.fromJSON(subscribeItem, Mocker.B);
        String jsonString = SimpleJsonConverter.toJSON(subscribeItem).toString();
        System.out.print(jsonString);
    }
}
