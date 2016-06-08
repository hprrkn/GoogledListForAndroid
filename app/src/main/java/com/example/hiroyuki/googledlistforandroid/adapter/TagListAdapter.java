package com.example.hiroyuki.googledlistforandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hiroyuki.googledlistforandroid.R;
import com.example.hiroyuki.googledlistforandroid.entity.Tag;

import java.util.ArrayList;

/**
 * Created by Hiroyuki on 2016/06/08.
 */
public class TagListAdapter extends ArrayAdapter<Tag> {
    private LayoutInflater layoutInflater;

    public TagListAdapter(Context c, int id, ArrayList<Tag> tagList) {
        super(c, id, tagList);
        this.layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.tag_list_item, parent, false);
        }

        Tag tag = getItem(position);
        ((TextView) convertView.findViewById(R.id.titleTextView)).setText(tag.getTagName());

        return convertView;
    }
}