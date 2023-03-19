package com.fuzhenfeng.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static javax.print.DocFlavor.URL.TEXT_PLAIN_UTF_8;

public class HttpObjectConverter {

    private HttpObjectConverter(){}

    public static HttpReq converter(FullHttpRequest httpRequest) {
        Iterator<Map.Entry<String, String>> iterator = httpRequest.headers().iterator();
        Map<String, String> header = new HashMap<>(httpRequest.headers().size());
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            header.put(next.getKey(), next.getValue());
        }
        String method = buildMethod(header, httpRequest.getMethod());
        ByteBuf content = httpRequest.content();
        byte[] bytes = ByteBufUtil.getBytes(content);
        String body = new String(bytes);
        return new HttpReq(httpRequest.uri(), method, header, body);
    }

    public static FullHttpResponse converter(HttpResp httpResp, HttpVersion httpVersion) {
        if(200 != httpResp.getCode()) {
            FullHttpResponse response = new DefaultFullHttpResponse(httpVersion, INTERNAL_SERVER_ERROR);
            return response;
        }
        byte[] bytes = getBytes(httpResp.getBody());
        FullHttpResponse response = new DefaultFullHttpResponse(httpVersion, OK, Unpooled.wrappedBuffer(bytes));
        response.headers()
                .set(CONTENT_TYPE, TEXT_PLAIN_UTF_8)
                .setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

    private static byte[] getBytes(String str) {
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // ignore
            return null;
        }
    }

    private static String buildMethod(Map<String, String> header, HttpMethod httpMethod) {
        String reqMethod = header.get("reqMethod");
        if(reqMethod == null || reqMethod.length() == 0) {
            return httpMethod.toString();
        }
        return reqMethod;
    }
}
