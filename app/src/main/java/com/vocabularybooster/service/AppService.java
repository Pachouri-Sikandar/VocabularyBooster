package com.vocabularybooster.service;

import com.vocabularybooster.parser.BaseVocabResponseParser;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by ankit on 26/2/16.
 */
public interface AppService {
    @GET("/words.json")
    void getVocabListApiCall(Callback<BaseVocabResponseParser> vocabResponseParserCallback);
}
