package com.example.hiroyuki.googledlistforandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hiroyuki.googledlistforandroid.Utils.Const;
import com.example.hiroyuki.googledlistforandroid.entity.APIResult;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.impl.cookie.BasicClientCookie;

import android.text.TextUtils;


public class LoginActivity extends AppCompatActivity {

    private static String APIUrl = Const.ip + "/android/api/login";

    private static final String loginTrue = "true";

    String editUserName = "";
    String editPassword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        assert loginButton != null;
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                editUserName = ((EditText) findViewById(R.id.editUseName)).getText().toString();
                editPassword = ((EditText)findViewById(R.id.editPassword)).getText().toString();
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("username", editUserName);
                params.add("password", editPassword);
                client.post(v.getContext(), APIUrl, params, new TextHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        final Gson gson = new Gson();
                        APIResult result = gson.fromJson(responseString, APIResult.class);
                        Intent intent;
                        if (TextUtils.equals(result.getLoginOk(), loginTrue)){
                            PersistentCookieStore myCookieStore = new PersistentCookieStore(v.getContext());
                            BasicClientCookie newCookie1 = new BasicClientCookie("token",result.getToken());
                            BasicClientCookie newCookie2 = new BasicClientCookie("uId",result.getUserId());
                            myCookieStore.addCookie(newCookie1);
                            myCookieStore.addCookie(newCookie2);
                            intent = new Intent(v.getContext(), IndexActivity.class);
                        } else {
                            intent = new Intent(v.getContext(), LoginActivity.class);
                        }
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
