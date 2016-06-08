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
import com.example.hiroyuki.googledlistforandroid.adapter.TagListAdapter;
import com.example.hiroyuki.googledlistforandroid.entity.Tag;
import com.example.hiroyuki.googledlistforandroid.entity.TagList;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

public class TagListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_list);

        GoogledListAsyncHttpClient client = new GoogledListAsyncHttpClient(this);
        client.get(this, Const.GET_TAG_LIST_API_URL, client.getParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Gson gson = new Gson();
                TagList tagList = gson.fromJson(responseString, TagList.class);
                ListView tagListView = (ListView) findViewById(R.id.tagListView);
                TagListAdapter tAdapter = new TagListAdapter(TagListActivity.this, 0, tagList.getTagArrayList());
                tagListView.setAdapter(tAdapter);

                tagListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(view.getContext(), ListActivity.class);
                        intent.putExtra("fromTagList", "yes");
                        intent.putExtra("selectedTag", (Tag)parent.getItemAtPosition(position));
                        startActivity(intent);
                    }
                });




            }
        });
    }
}
