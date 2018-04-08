package com.janiszhang.myokhttp.okhttp;

import com.janiszhang.myokhttp.okhttp.callback.CommonJsonCallBack;
import com.janiszhang.myokhttp.okhttp.handle.ResponseDataHandle;
import com.janiszhang.myokhttp.okhttp.ssl.HttpsUtils;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 *
 * @author janiszhang
 * @date 2018/4/4
 *
 * 用来发送get，post请求的工具类，包括设置一些请求的共用参数
 */

public class CommonOkHttpClient {

    private static final int TIME_OUT = 30;
    private static OkHttpClient sOkHttpClient;

    static {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        });

        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.followSslRedirects(true);

        okHttpClientBuilder.sslSocketFactory(HttpsUtils.getSslSocketFactory());
        sOkHttpClient = okHttpClientBuilder.build();
    }



    public static Call get(Request request, ResponseDataHandle handle) {

        Call call = sOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallBack(handle));
        return call;
    }


    public static Call post(Request request, ResponseDataHandle handle) {
        Call call = sOkHttpClient.newCall(request);
        call.enqueue(new CommonJsonCallBack(handle));

        return call;
    }


}
