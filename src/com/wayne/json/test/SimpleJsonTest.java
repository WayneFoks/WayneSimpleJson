package com.wayne.json.test;

import com.wayne.json.SimpleJson;
import com.wayne.json.utils.SimpleJsonUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SimpleJsonTest {

    @Test
    public void parseJson() throws Exception {
        String input = Mocker.B;
        SubscribeItem subscribeItem = new SubscribeItem();
        SimpleJsonUtils.parseJson(subscribeItem, input);
        String jsonString = SimpleJson.toJson(subscribeItem).toString();
        System.out.println(input);
        System.out.println(jsonString);
        assertTrue(Mocker.B.equals(jsonString));
    }
}