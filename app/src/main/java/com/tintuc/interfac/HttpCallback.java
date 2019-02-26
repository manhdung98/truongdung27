package com.tintuc.interfac;

import android.app.DownloadManager;

import java.io.IOException;

import okhttp3.Request;

public interface HttpCallback {
    void onSuccess(String s);
    void onStart();
    void onFailure(Request request, IOException e);
}
