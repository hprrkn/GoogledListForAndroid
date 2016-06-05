package com.example.hiroyuki.googledlistforandroid.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hiroyuki on 2016/06/05.
 */
public class Word implements Serializable {
    @SerializedName("id")
    private int id;
    @SerializedName("wordtitle")
    private String title;
    @SerializedName("memo")
    private String memo;
    private ArrayList<Tag> tagArrayList;
    private String created_at;
    private String updated_at;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public ArrayList<Tag> getTagArrayList() {
        return tagArrayList;
    }

    public void setTagArrayList(ArrayList<Tag> tagArrayList) {
        this.tagArrayList = tagArrayList;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
