package com.example.hiroyuki.googledlistforandroid.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Hiroyuki on 2016/06/08.
 */
public class TagList {
    @SerializedName("tagList")
    public ArrayList<Tag> tagArrayList;

    public ArrayList<Tag> getTagArrayList() {
        return tagArrayList;
    }

    public void setTagArrayList(ArrayList<Tag> tagArrayList) {
        this.tagArrayList = tagArrayList;
    }
}
