package com.janiszhang.myokhttp.okhttp.handle;

import com.janiszhang.myokhttp.okhttp.listener.ResponseDataListener;

/**
 *
 * @author janiszhang
 * @date 2018/4/4
 */

public class ResponseDataHandle {
    public ResponseDataListener mListener = null;
    public Class<?> mClass = null;

    public ResponseDataHandle(ResponseDataListener listener) {
        this.mListener = listener;
    }

    public ResponseDataHandle(ResponseDataListener listener, Class clazz){
        this.mListener = listener;
        this.mClass = clazz;
    }
}
