package com.vocabularybooster.parser;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ankit on 26/2/16.
 */
public class BaseVocabResponseParser {
    @SerializedName("words")
    private ArrayList<VocabKeysParser> words;

    public ArrayList<VocabKeysParser> getWords() {
        return words;
    }

    public void setWords(ArrayList<VocabKeysParser> words) {
        this.words = words;
    }
}
