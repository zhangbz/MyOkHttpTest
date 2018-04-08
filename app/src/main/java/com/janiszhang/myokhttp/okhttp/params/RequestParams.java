package com.janiszhang.myokhttp.okhttp.params;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by janiszhang on 2018/4/4.
 */

public class RequestParams {

    public ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<>();


    public RequestParams() {
        this(null);
    }

    public RequestParams(final String key, final String value) {
        put(key, value);
    }


    public RequestParams(Map<String, String> source) {
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }



    public void put(String key, String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
    }
}
