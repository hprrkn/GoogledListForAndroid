package com.example.hiroyuki.googledlistforandroid.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;



/**
 * Created by Hiroyuki on 2016/05/30.
 */
public class GoogledListAsyncHttpClient extends AsyncHttpClient {
    private RequestParams params;

    public GoogledListAsyncHttpClient(Context cxt){

        SharedPreferences preferences = cxt.getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        String userId = preferences.getString("uId", "");

        this.params = new RequestParams();
        params.add("userId", userId);
        params.add("token", token);
    }

    public RequestParams getParams(){
        return this.params;
    }

}
