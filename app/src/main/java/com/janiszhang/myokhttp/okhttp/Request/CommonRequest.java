package com.janiszhang.myokhttp.okhttp.Request;

import com.janiszhang.myokhttp.okhttp.params.RequestParams;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by janiszhang on 2018/4/4.
 */

public class CommonRequest {

    public static Request createGetRequest(String url, RequestParams params) {
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (params != null)  {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }

        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() -1)).get().build();
    }


    public static Request createPostRequest(String url, RequestParams params) {
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                formBodyBuilder.add(entry.getKey(), entry.getValue());
            }
        }

        FormBody formBody = formBodyBuilder.build();

        return new Request.Builder().url(url).post(formBody).build();
    }
}
