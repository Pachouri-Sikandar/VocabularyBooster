package com.vocabularybooster.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.query.Select;
import com.vocabularybooster.parser.VocabKeysParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankit on 26/2/16.
 */
public class VocabModel extends Model {

    @Column(name = "vocab_id")
    private int vocabId;
    @Column(name = "word")
    private String word;
    @Column(name = "variant")
    private int variant;
    @Column(name = "meaning")
    private String meaning;
    @Column(name = "ratio")
    private double ratio;

    public VocabModel() {
    }

    public VocabModel(VocabKeysParser vocabKeysParser) {
        this.vocabId = vocabKeysParser.getId();
        this.word = vocabKeysParser.getWord();
        this.variant = vocabKeysParser.getVariant();
        this.meaning = vocabKeysParser.getMeaning();
        this.ratio = vocabKeysParser.getRatio();
    }

    public int getVocabId() {
        return vocabId;
    }

    public String getWord() {
        return word;
    }

    public int getVariant() {
        return variant;
    }

    public String getMeaning() {
        return meaning;
    }

    public double getRatio() {
        return ratio;
    }

    public static void saveVocabList(ArrayList<VocabKeysParser> vocabKeysParserArrayList) {
        if (vocabKeysParserArrayList.size() != 0 && !vocabKeysParserArrayList.isEmpty()) {
            ActiveAndroid.beginTransaction();
            try {
                for (VocabKeysParser vocabKeysParser : vocabKeysParserArrayList) {
                    new VocabModel(vocabKeysParser).save();
                }
                ActiveAndroid.setTransactionSuccessful();
            } finally {
                ActiveAndroid.endTransaction();
            }
        }
    }

    public static ArrayList<VocabKeysParser> fetchVocabModel() {
        ArrayList<VocabKeysParser> vocabKeysParserArrayList = new ArrayList<>();
        List<VocabModel> vocabModelList = new Select().from(VocabModel.class).execute();

        if (vocabModelList.size() != 0 && !vocabModelList.isEmpty()) {
            for (VocabModel vocabModel : vocabModelList) {
                vocabKeysParserArrayList.add(new VocabKeysParser(vocabModel));
            }
        }
        return vocabKeysParserArrayList;
    }
}
