package com.example.hiroyuki.googledlistforandroid.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Hiroyuki on 2016/05/08.
 */
public class IndexResult {

    @SerializedName("coms")
    private List<CountOfMonth> comList;

    @SerializedName("tagList")
    private List<Tag> tagList;

    public List<CountOfMonth> getComList() {
        return comList;
    }

    public void setComList(List<CountOfMonth> comList) {
        this.comList = comList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

}
