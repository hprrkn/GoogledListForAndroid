package com.example.hiroyuki.googledlistforandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hiroyuki.googledlistforandroid.adapter.ComAdapter;
import com.example.hiroyuki.googledlistforandroid.entity.CountOfMonth;
import com.example.hiroyuki.googledlistforandroid.entity.IndexResult;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;

public class IndexActivity extends AppCompatActivity {

    private final String INDEX_API_URL = Const.ip + "/android/api/index";
    private final String ADD_WORD_API_URL = Const.ip + "/android/api/add_word";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        String uId = "";
        String token = "";
        PersistentCookieStore myCookieStore = new PersistentCookieStore(getApplicationContext());
        List<Cookie> cookies = myCookieStore.getCookies();
        for (Cookie c : cookies) {
            if (c.getName().equals("uId")) {
                uId = c.getValue();
            } else if (c.getName().equals("token")) {
                token = c.getValue();
            }
        }

        RequestParams params = new RequestParams();
        params.add("userId", uId);
        params.add("token", token);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(this, INDEX_API_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(IndexActivity.this, "index通信失敗なり〜", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                final Gson gson = new Gson();
                IndexResult result = gson.fromJson(responseString, IndexResult.class);

                Toast.makeText(IndexActivity.this, "index成功なり", Toast.LENGTH_SHORT).show();
                ListView indexListView = (ListView) findViewById(R.id.indexListView);
                ComAdapter adapter = new ComAdapter(IndexActivity.this, 0, (ArrayList<CountOfMonth>) result.getComList());
                assert indexListView != null;
                indexListView.setAdapter(adapter);
            }
        });

        Button button = (Button) findViewById(R.id.addButton);
        assert button != null;
        final String finalUId = uId;
        final String finalToken = token;
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                assert findViewById(R.id.add_title) != null;
                EditText editTitle = (EditText)findViewById(R.id.add_title);
                assert editTitle != null;
                String getTitle = editTitle.getText().toString();
                assert findViewById(R.id.add_memo) != null;
                EditText editMemo = (EditText)findViewById(R.id.add_memo);
                assert editMemo != null;
                String getMemo = editMemo.getText().toString();
                final RequestParams params = new RequestParams();
                params.add("token", finalToken);
                params.add("userId", finalUId);
                params.add("title", getTitle);
                params.add("memo", getMemo);
                AsyncHttpClient addClient = new AsyncHttpClient();
                addClient.post(v.getContext(), ADD_WORD_API_URL, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(IndexActivity.this, "通信失敗なり〜", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Toast.makeText(IndexActivity.this, params.toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), ListActivity.class);
                        intent.putExtra("","");
                        startActivity(intent);
                    }
                });
            }
        });

    }
}
