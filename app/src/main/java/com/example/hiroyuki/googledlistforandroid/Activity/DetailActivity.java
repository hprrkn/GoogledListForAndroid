package com.example.hiroyuki.googledlistforandroid.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hiroyuki.googledlistforandroid.R;
import com.example.hiroyuki.googledlistforandroid.entity.Word;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Word selectedWord = (Word) getIntent().getSerializableExtra("selectedWord");

        TextView titleTextView = (TextView) findViewById(R.id.titleTextView);
        TextView memoTextView = (TextView) findViewById(R.id.memoTextView);
        titleTextView.setText(selectedWord.getTitle());
        if (selectedWord.getMemo() != null) {
            memoTextView.setText(selectedWord.getMemo());
        }

    }
}
