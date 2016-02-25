package com.vocabularybooster.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;

import com.google.gson.Gson;
import com.vocabularybooster.BaseApplication;
import com.vocabularybooster.R;
import com.vocabularybooster.activity.BaseActivity;
import com.vocabularybooster.model.VocabModel;
import com.vocabularybooster.parser.BaseVocabResponseParser;
import com.vocabularybooster.parser.VocabKeysParser;
import com.vocabularybooster.service.AppService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ankit on 26/2/16.
 */
public class CommonUtils {

    public static final String CHECK_BROADCAST = "CHECK_BROADCASTt";
    public static final String CHECK_ACTION_DONE = "CHECK_ACTION_DONE";

    public boolean isInternetConnected(Context context) {
        boolean isInternetConnected = false;
        try {
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context
                    .CONNECTIVITY_SERVICE);
            if (connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo()
                    .isAvailable() && connManager.getActiveNetworkInfo().isConnected()) {
                isInternetConnected = true;
            }
        } catch (Exception ex) {
            isInternetConnected = false;
        }
        return isInternetConnected;
    }

    public void getVocabularyApiCall(Context context) {
        if (isInternetConnected(context)) {
            AppService service;
            if (context instanceof BaseActivity) {
                service = ((BaseActivity) context).getService();
            } else {
                service = ((BaseApplication) context).appService;
            }
            service.getVocabListApiCall(getVocabResponseCallBack(context));
        } else if (context instanceof BaseActivity) {
            View view = ((BaseActivity) context).findViewById(android.R.id.content);
            ((BaseActivity) context).showSnackBar(context, view, context.getString(R.string
                    .internet_not_found));
        }
    }

    private Callback<BaseVocabResponseParser> getVocabResponseCallBack(final Context context) {
        return new Callback<BaseVocabResponseParser>() {
            @Override
            public void success(BaseVocabResponseParser baseVocabResponseParser, Response response) {
                if (baseVocabResponseParser != null && baseVocabResponseParser.getWords() != null &&
                        !baseVocabResponseParser.getWords().isEmpty()) {

                    Gson gson = new Gson();
                    Intent intent = new Intent();
                    intent.setAction(CHECK_ACTION_DONE);
                    intent.putExtra(CHECK_BROADCAST, gson.toJson(baseVocabResponseParser));

                    LocalBroadcastManager manager = LocalBroadcastManager.getInstance(context);
                    manager.sendBroadcast(intent);

                    saveVocabListInLocalDB(baseVocabResponseParser.getWords());
                } else if (context instanceof BaseActivity) {
                    View view = ((BaseActivity) context).findViewById(android.R.id.content);
                    ((BaseActivity) context).showSnackBar(context, view, context.getString(R.string
                            .error_message));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (context instanceof BaseActivity) {
                    View view = ((BaseActivity) context).findViewById(android.R.id.content);
                    ((BaseActivity) context).showSnackBar(context, view, context.getString(R.string
                            .error_message));
                }
            }
        };
    }

    private void saveVocabListInLocalDB(final ArrayList<VocabKeysParser> vocabKeysParserArrayList) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                VocabModel.saveVocabList(vocabKeysParserArrayList);
                return null;
            }
        }.execute();
    }
}
