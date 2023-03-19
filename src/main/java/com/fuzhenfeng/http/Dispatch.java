package com.fuzhenfeng.http;

/**
 * 分发请求
 * 服务器启动后，应用需要实现此接口完成请求到应用的分发。
 * @author fuzhenfeng
 */
public interface Dispatch {
    /**
     * 分发
     * @param httpReq
     * @return
     */
    HttpResp happyCall(HttpReq httpReq);
}
