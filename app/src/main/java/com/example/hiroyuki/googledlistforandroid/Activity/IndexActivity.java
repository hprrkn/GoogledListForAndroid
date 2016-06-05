package com.example.hiroyuki.googledlistforandroid.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.hiroyuki.googledlistforandroid.R;
import com.example.hiroyuki.googledlistforandroid.Utils.Const;
import com.example.hiroyuki.googledlistforandroid.Utils.GoogledListAsyncHttpClient;
import com.example.hiroyuki.googledlistforandroid.adapter.ComAdapter;
import com.example.hiroyuki.googledlistforandroid.entity.CountOfMonth;
import com.example.hiroyuki.googledlistforandroid.entity.IndexResult;
import com.google.gson.Gson;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.cookie.Cookie;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        // ログインチェック
        PersistentCookieStore myCookieStore = new PersistentCookieStore(getApplicationContext());
        List<Cookie> cookies = myCookieStore.getCookies();
        Boolean loginNG = true;
        for (Cookie c : cookies) {
            if (c.getName().equals("token")) {
                loginNG = false;
            }
        }
        if (loginNG){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        GoogledListAsyncHttpClient client = new GoogledListAsyncHttpClient(this);
        client.get(this, Const.INDEX_API_URL, client.getParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                final Gson gson = new Gson();
                IndexResult result = gson.fromJson(responseString, IndexResult.class);

                ListView indexListView = (ListView) findViewById(R.id.indexListView);
                ComAdapter adapter = new ComAdapter(IndexActivity.this, 0, (ArrayList<CountOfMonth>) result.getComList());
                assert indexListView != null;
                indexListView.setAdapter(adapter);

                indexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(view.getContext(), ListActivity.class);
                        intent.putExtra("ym", ((CountOfMonth) parent.getItemAtPosition(position)).getMonth());
                        startActivity(intent);
                    }
                });
            }
        });


        Button button = (Button) findViewById(R.id.addButton);
        assert button != null;
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

                GoogledListAsyncHttpClient addClient = new GoogledListAsyncHttpClient(v.getContext());
                addClient.getParams().add("title", getTitle);
                addClient.getParams().add("memo", getMemo);
                addClient.post(v.getContext(), Const.ADD_WORD_API_URL, addClient.getParams(), new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Intent intent = new Intent(v.getContext(), ListActivity.class);
                        intent.putExtra("","");
                        startActivity(intent);
                    }
                });
            }
        });

    }
}
