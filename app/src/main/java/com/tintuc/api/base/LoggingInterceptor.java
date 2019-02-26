package com.tintuc.api.base;

import com.tintuc.until.LogUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

class LoggingInterceptor  implements Interceptor {
    private static final String F_BREAK = " %n";
    private static final String F_URL = " %s";
    private static final String F_TIME = " in %.1fms";
    private static final String F_HEADER = "%s";
    private static final String F_RESPONE = F_BREAK + "Respone: %d";
    private static final String F_BODY = "body: %s";

    private static final String F_BREAKER = F_BREAK+ "-----------------" + F_BREAK;
    private static final String F_REQUEST_WITHOUT_BODY = F_URL + F_TIME + F_BREAK + F_HEADER;
    private static final String F_RESPONE_WITHOUT_BODY = F_RESPONE + F_BREAK + F_HEADER + F_BREAKER;
    private static final String F_REQUEST_WITH_BODY = F_URL + F_TIME + F_BREAK + F_HEADER + F_BODY + F_BREAK;
    private static final String F_RESPONE_WITH_BODY = F_RESPONE + F_BREAK + F_HEADER + F_BODY + F_BREAK + F_BREAKER;



    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        MediaType contentType = null;
        String bodyString = null;

        if (response.body() != null){
            contentType = response.body().contentType();
            bodyString = response.body().string();
        }

        double time = (t2 - t1) / 1e6d;

        if (request.method().equals("GET")){
            LogUtil.d("OKHTTP", String.format("GET" + F_REQUEST_WITHOUT_BODY + F_RESPONE_WITH_BODY, request.url()));
        }else if (request.method().equals("POST")){
            LogUtil.d("OKHTTP", String.format("GET" + F_REQUEST_WITHOUT_BODY + F_RESPONE_WITH_BODY, request.url()));
        }

        if (response.body() != null){
            ResponseBody body = ResponseBody.create(contentType, bodyString);
            return response.newBuilder().body(body).build();
        }
        else {
            return response;
        }

    }
}
