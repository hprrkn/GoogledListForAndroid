package com.example.hiroyuki.googledlistforandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.cookie.Cookie;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button checkButton = (Button) findViewById(R.id.loginCheckButton);
        assert checkButton != null;

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersistentCookieStore myCookieStore = new PersistentCookieStore(getApplicationContext());
                List<Cookie> cookies = myCookieStore.getCookies();
                Intent intent;
                for (Cookie c : cookies) {
                    if (c.getName().equals("token")) {
                        intent = new Intent(v.getContext(), IndexActivity.class);
                        startActivity(intent);
                        return;
                    }
                }
                intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
