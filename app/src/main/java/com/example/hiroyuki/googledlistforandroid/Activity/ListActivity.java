package com.example.hiroyuki.googledlistforandroid.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.hiroyuki.googledlistforandroid.R;
import com.example.hiroyuki.googledlistforandroid.Utils.Const;
import com.example.hiroyuki.googledlistforandroid.Utils.GoogledListAsyncHttpClient;
import com.example.hiroyuki.googledlistforandroid.adapter.WordListAdapter;
import com.example.hiroyuki.googledlistforandroid.entity.Word;
import com.example.hiroyuki.googledlistforandroid.entity.WordList;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        GoogledListAsyncHttpClient client = new GoogledListAsyncHttpClient(this);
        String url = Const.BASE_GET_LIST_API_URL + getIntent().getStringExtra("ym");
        client.get(this, url, client.getParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                WordList wordList = gson.fromJson(responseString, WordList.class);
                ListView wordListView = (ListView) findViewById(R.id.wordListView);
                WordListAdapter wAdapter = new WordListAdapter(ListActivity.this, 0, wordList.getWordList());
                wordListView.setAdapter(wAdapter);

                wordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra("selectedWord", (Word)parent.getItemAtPosition(position));
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
