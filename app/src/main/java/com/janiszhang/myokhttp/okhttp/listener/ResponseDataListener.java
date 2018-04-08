package com.janiszhang.myokhttp.okhttp.listener;

/**
 *
 * @author janiszhang
 * @date 2018/4/4
 *
 * 业务逻辑真正处理的地方
 *
 * 为什么不直接使用OkHttp的Callback，而是额外自定义一个listener？
 * 之所以这样做，是为了将业务逻辑和通用的网络处理完全分开，
 * 统一的网络处理可以统一放在Callback中处理，
 * 然后再调用自定义的listener对业务逻辑进行处理。
 * 另外一个重要原因是：Callback的onFailure只针对网络请求的异常情况，
 * 而实际开发中，我们不仅要关注网络异常，还要关注业务逻辑异常，
 * 通过自定义listener可以做到一点，因为callback的接口由框架自身调用，
 * 而listener的接口我们可以自由调用。
 */

public interface ResponseDataListener {

    /**
     * 请求成功回调事件处理
     * @param responseObj
     */
    void onSuccess(Object responseObj);

    /**
     * 请求失败回调时间处理
     * @param reasonObj
     */
    void onFailure(Object reasonObj);
}
