package com.vocabularybooster.parser;

import com.google.gson.annotations.SerializedName;
import com.vocabularybooster.model.VocabModel;

/**
 * Created by ankit on 26/2/16.
 */
public class VocabKeysParser {

    @SerializedName("id")
    private int id;
    @SerializedName("word")
    private String word;
    @SerializedName("variant")
    private int variant;
    @SerializedName("meaning")
    private  String meaning;
    @SerializedName("ratio")
    private double ratio;

    public VocabKeysParser() {
    }

    public VocabKeysParser(VocabModel vocabModel) {
        this.id = vocabModel.getVocabId();
        this.word = vocabModel.getWord();
        this.variant = vocabModel.getVariant();
        this.meaning = vocabModel.getMeaning();
        this.ratio = vocabModel.getRatio();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getVariant() {
        return variant;
    }

    public void setVariant(int variant) {
        this.variant = variant;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
