package com.example.administrator.myapplication.Activity;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/12/10.
 */

public class Server {
    static OkHttpClient client;

    static{
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
    }

    public static OkHttpClient getSharedClient(){
        return client;
    }

    public  static Request.Builder requestBuilderWithApi(String api){
        return new Request.Builder()
                .url("http://172.27.0.37:8080/membercenter/api"+api);
    }

}
