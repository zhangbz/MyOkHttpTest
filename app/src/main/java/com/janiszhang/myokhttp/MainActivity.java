package com.janiszhang.myokhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.janiszhang.myokhttp.okhttp.CommonOkHttpClient;
import com.janiszhang.myokhttp.okhttp.Request.CommonRequest;
import com.janiszhang.myokhttp.okhttp.exception.OkHttpException;
import com.janiszhang.myokhttp.okhttp.handle.ResponseDataHandle;
import com.janiszhang.myokhttp.okhttp.listener.ResponseDataListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author janiszhang
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button) findViewById(R.id.btn_get_1);
        btn1.setOnClickListener(this);

        Button btn2 = (Button) findViewById(R.id.btn_get_2);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_1:

                final Request request = new Request.Builder().url("https://www.baidu.com").build();
                OkHttpClient okHttpClient = new OkHttpClient();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("OkHttpTest", "(1)onFailure, " + e.getMessage());

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("OkHttpTest", "(1)onResponse, " + response.toString());
                    }
                });
                break;

            case R.id.btn_get_2:
                CommonOkHttpClient.get(CommonRequest.createGetRequest("https://www.baidu.com", null),
                        new ResponseDataHandle(new ResponseDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        Log.e("OkHttpTest", "(2)onSuccess, " + responseObj.toString());

                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        Log.e("OkHttpTest", "(2)onFailure, " + ((OkHttpException)reasonObj).getMessage());

                    }
                }));

                break;

            default:

                break;
        }
    }
}
