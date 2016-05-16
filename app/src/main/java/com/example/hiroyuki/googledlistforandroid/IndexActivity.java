package com.example.hiroyuki.googledlistforandroid;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
                Toast.makeText(IndexActivity.this, "通信失敗なり〜", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                final Gson gson = new Gson();
                IndexResult result = gson.fromJson(responseString, IndexResult.class);

                ListView indexListView = (ListView) findViewById(R.id.indexListView);
                ComAdapter adapter = new ComAdapter(IndexActivity.this, 0, (ArrayList<CountOfMonth>) result.getComList());
                assert indexListView != null;
                indexListView.setAdapter(adapter);
            }
        });

    }

    public class ComAdapter extends ArrayAdapter<CountOfMonth> {
        private LayoutInflater layoutInflater;

        public ComAdapter(Context c, int id, ArrayList<CountOfMonth> comList) {
            super(c, id, comList);
            this.layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.index_list_item, parent, false);
            }

            CountOfMonth com = getItem(position);
            ((TextView)convertView.findViewById(R.id.monthTextView)).setText(com.getMonth());
            ((TextView)convertView.findViewById(R.id.countTextView)).setText(String.valueOf(com.getCount()));

            return convertView;
        }
    }
}
