package com.janiszhang.myokhttp.okhttp.callback;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.janiszhang.myokhttp.okhttp.exception.OkHttpException;
import com.janiszhang.myokhttp.okhttp.handle.ResponseDataHandle;
import com.janiszhang.myokhttp.okhttp.listener.ResponseDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 *
 * @author janiszhang
 * @date 2018/4/4
 */

public class CommonJsonCallBack implements Callback {


    protected final String RESULT_CODE = "errorCode";
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "errorMsg";
    protected final String EMPTY_MSG = "";

    protected final int NETWORK_ERROR = -1;
    protected final int JSON_ERROR = -2;
    protected final int OTHER_ERROR = -3;

    /**
     * 将其他线程的数据转发到UI线程
     */
    private Handler mDeliveryHandler;

    private ResponseDataListener mListener;
    private Class<?> mClass;

    public CommonJsonCallBack(ResponseDataHandle handle) {
        this.mListener = handle.mListener;
        this.mClass = handle.mClass;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());

    }

    @Override
    public void onFailure(Call call, final IOException e) {

        //此时还在非UI线程，需要转发到主线程
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(NETWORK_ERROR, e.getMessage()));
            }
        });
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        final String result = response.body().string();
//        response.headers();todo 处理cookie
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                handleResponse(result);
            }
        });
    }

    private void handleResponse(Object responseObj) {
        if (responseObj == null) {
            mListener.onFailure(new OkHttpException(NETWORK_ERROR, EMPTY_MSG));
            return;
        }

        try {
            JSONObject result = new JSONObject(responseObj.toString());
            if (result.has(RESULT_CODE)) {
              if (result.optInt(RESULT_CODE) == RESULT_CODE_VALUE) {
                  if (mClass == null) {
                      mListener.onSuccess(result);
                  } else {
                      Object obj = JSON.parseObject(responseObj.toString(), mClass);
                      if (obj != null) {
                          mListener.onSuccess(obj);
                      } else {
                          mListener.onFailure(new OkHttpException(JSON_ERROR, EMPTY_MSG));
                      }
                  }
              } else {
                  if (result.has(ERROR_MSG)) {
                      mListener.onFailure(new OkHttpException(result.optInt(RESULT_CODE), result.optString(ERROR_MSG)));
                  } else {
                      mListener.onFailure(new OkHttpException(result.optInt(RESULT_CODE), EMPTY_MSG));
                  }
              }

            } else {
                if (result.has(ERROR_MSG)) {
                    mListener.onFailure(new OkHttpException(OTHER_ERROR, result.optString(ERROR_MSG)));
                }
            }
        }  catch (JSONException e) {
            mListener.onFailure(new OkHttpException(JSON_ERROR, e.getMessage()));
        }

    }
}
