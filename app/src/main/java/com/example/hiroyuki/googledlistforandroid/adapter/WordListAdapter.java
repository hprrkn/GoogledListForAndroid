package com.example.hiroyuki.googledlistforandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hiroyuki.googledlistforandroid.R;
import com.example.hiroyuki.googledlistforandroid.entity.Word;

import java.util.ArrayList;

/**
 * Created by Hiroyuki on 2016/06/05.
 */

public class WordListAdapter extends ArrayAdapter<Word> {
    private LayoutInflater layoutInflater;

    public WordListAdapter(Context c, int id, ArrayList<Word> wordList) {
        super(c, id, wordList);
        this.layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.word_list_item, parent, false);
        }

        Word word = getItem(position);
        ((TextView)convertView.findViewById(R.id.titleTextView)).setText(word.getTitle());

        return convertView;
    }
}