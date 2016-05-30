package com.example.hiroyuki.googledlistforandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hiroyuki.googledlistforandroid.R;
import com.example.hiroyuki.googledlistforandroid.entity.CountOfMonth;

import java.util.ArrayList;

/**
 * Created by Hiroyuki on 2016/05/30.
 */
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
