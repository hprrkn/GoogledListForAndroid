package com.example.hiroyuki.googledlistforandroid.Utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import java.util.List;

import cz.msebera.android.httpclient.cookie.Cookie;


/**
 * Created by Hiroyuki on 2016/05/30.
 */
public class GoogledListAsyncHttpClient extends AsyncHttpClient {
    private RequestParams params;

    public GoogledListAsyncHttpClient(Context cxt){
        String uId = "";
        String token = "";
        PersistentCookieStore myCookieStore = new PersistentCookieStore(cxt);
        List<Cookie> cookies = myCookieStore.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("uId")) {
                uId = c.getValue();
            } else if (c.getName().equals("token")) {
                token = c.getValue();
            }
        }

        this.params = new RequestParams();
        params.add("userId", uId);
        params.add("token", token);
    }

    public RequestParams getParams(){
        return this.params;
    }

}
