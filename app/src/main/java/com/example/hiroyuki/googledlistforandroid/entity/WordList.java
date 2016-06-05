package com.example.hiroyuki.googledlistforandroid.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiroyuki on 2016/06/05.
 */
public class WordList {
    @SerializedName("wordList")
    public ArrayList<Word> wordList;

    public ArrayList<Word> getWordList() {
        return wordList;
    }

    public void setWordList(ArrayList<Word> wordList) {
        this.wordList = wordList;
    }
}
