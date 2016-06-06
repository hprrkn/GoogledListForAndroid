package com.example.hiroyuki.googledlistforandroid.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hiroyuki.googledlistforandroid.R;
import com.example.hiroyuki.googledlistforandroid.Utils.Const;
import com.example.hiroyuki.googledlistforandroid.Utils.GoogledListAsyncHttpClient;
import com.example.hiroyuki.googledlistforandroid.adapter.ComAdapter;
import com.example.hiroyuki.googledlistforandroid.entity.CountOfMonth;
import com.example.hiroyuki.googledlistforandroid.entity.IndexResult;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // ログインチェック
        SharedPreferences preferences = getSharedPreferences("LoginData", Context.MODE_PRIVATE);
        String token = preferences.getString("token", "");
        String userId = preferences.getString("uId", "");
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userId)){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        // toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        assert toolbar != null;
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.logOutMenu){
                    new AlertDialog.Builder(toolbar.getContext())
                            .setTitle("ログアウト確認")
                            .setMessage("ほんまにログアウトするんか？")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(IndexActivity.this, "ログアウトしました。", Toast.LENGTH_SHORT).show();                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }
                return true;
            }
        });

        // API通信
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


        // button
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
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM");
                        intent.putExtra("ym",fmt.format(c.getTime()));
                        startActivity(intent);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
