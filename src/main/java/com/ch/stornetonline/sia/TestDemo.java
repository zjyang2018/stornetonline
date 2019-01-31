package com.ch.stornetonline.sia;

import org.json.JSONArray;

import java.io.IOException;

public class TestDemo {
    public static void main(String[] args) throws IOException {
        SiaObject so = new SiaObject();
      /*  JSONArray result = so.walletInit("");

        System.out.println(result.get(0));*/
      // String seed = "gymnast toffee water washing jolted syringe sword gumball irate piloted onto blip tycoon pinched cadets general jester opened wonders identity urban smelting fall custom wonders roared upright upkeep adventure";
        JSONArray result = so.walletUnlock("1");
        System.out.println(result);

        /*JSONArray address = so.walletAddress();
        System.out.println(address);*/
    }
}
