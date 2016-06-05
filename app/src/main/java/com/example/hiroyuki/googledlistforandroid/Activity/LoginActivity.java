package com.example.hiroyuki.googledlistforandroid.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hiroyuki.googledlistforandroid.R;
import com.example.hiroyuki.googledlistforandroid.Utils.Const;
import com.example.hiroyuki.googledlistforandroid.entity.APIResult;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

import android.text.TextUtils;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

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
                client.post(v.getContext(), Const.LOGIN_API_URL, params, new TextHttpResponseHandler() {

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        final Gson gson = new Gson();
                        APIResult result = gson.fromJson(responseString, APIResult.class);
                        Intent intent;
                        if (TextUtils.equals(result.getLoginOk(), loginTrue)){

                            // SharedPreferencesにtokenとuserIdを保存
                            SharedPreferences preferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token", result.getToken());
                            editor.putString("uId", result.getUserId());
                            editor.apply();
                            intent = new Intent(v.getContext(), IndexActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "IDかPWが違います。", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
