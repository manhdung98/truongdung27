package com.tintuc.api;

import android.content.Context;

import com.manhdung.news.MainActivity;
import com.tintuc.api.base.BaseOkhttp;
import com.tintuc.interfac.HttpCallback;
import com.tintuc.interfac.HttpCallback;
import com.tintuc.until.Define;
import com.tintuc.until.LogUtil;

import java.io.IOException;

import javax.xml.datatype.Duration;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsApi {
    public static void apiEx(Context context, HttpCallback httpCallback){
        BaseOkhttp baseOkhttp = new BaseOkhttp.Builder()
                                        .setHttpCallback(httpCallback)
                                        .setContext(context).setWantShowDialog(true)
                    .setWantDialogCancelable(true).setTitle(".....").setMessage("Loading...").build();

        OkHttpClient okHttpClient = new OkHttpClient();
       // String url = "https://www.24h.com.vn";

        final Request request = new Request.Builder()
                .url(Define.API_EXAMPLE)
                .build();
        okHttpClient.newCall(request).enqueue(baseOkhttp);

    }

    public static void getListPost(Context context,int categoryId, int limit, int offset, HttpCallback httpCallback) {
        BaseOkhttp baseOkhttp = new BaseOkhttp.Builder()
                .setHttpCallback(httpCallback)
                .setContext(context).setWantShowDialog(true)
                .setWantDialogCancelable(true).setTitle(".....").setMessage("Loading...").build();

        OkHttpClient okHttpClient = new OkHttpClient();
        String url = Define.API_GET_LIST_POST +"?category_id=" + categoryId + "&limit=" + limit + "&offset=" + offset ;
        LogUtil.d("getListpost", url);

        final Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(baseOkhttp);

    }

    public static void getPostDetail(Context context, int postId, HttpCallback httpCallback) {
        BaseOkhttp baseOkhttp = new BaseOkhttp.Builder()
                .setHttpCallback(httpCallback)
                .setContext(context).setWantShowDialog(true)
                .setWantDialogCancelable(true).setTitle(".....").setMessage("Loading...").build();

        OkHttpClient okHttpClient = new OkHttpClient();
        String url = Define.API_GET_POST_DETAIL + "?post_id" + postId;

        final Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(baseOkhttp);

    }

}
