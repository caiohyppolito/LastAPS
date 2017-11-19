package com.unip.apppedido.utils;

public class ResponseJsonUtil {
    public static String getListJson(String json){
        if(json != null){
            int startIndex = json.indexOf("[");
            int endIndex = json.indexOf("]");

            return json.substring(startIndex, endIndex + 1);
        }

        return null;
    }
}
