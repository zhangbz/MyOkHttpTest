package com.janiszhang.myokhttp.okhttp.exception;

/**
 *
 * @author janiszhang
 * @date 2018/4/4
 *
 * 自定义异常类，返回errorCode和errorMsg到业务层
 */

public class OkHttpException extends Exception {
    /**
     * 服务端返回的错误码
     */
    private int errorCode;

    /**
     * 服务端返回的错误信息
     */
    private Object errorMsg;


    public OkHttpException(int errorCode, Object errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;

    }

    public int getErrorCode() {
        return errorCode;
    }

    public Object getErrorMsg() {
        return errorMsg;
    }
}
