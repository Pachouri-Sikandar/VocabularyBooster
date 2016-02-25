package com.vocabularybooster;

import android.app.Application;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.vocabularybooster.service.AppService;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by ankit on 26/2/16.
 */
public class BaseApplication extends Application {
    private static String LOG_TAG = BaseApplication.class.getName();
    private static final String BASE_URL = "http://appsculture.com/vocab";
    public RestAdapter restAdapter;
    public AppService appService;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(LOG_TAG, "On Create");
        ActiveAndroid.initialize(this);

        restAdapter = initializeRestAdapter();
        appService = initializeAppService();
    }

    private RestAdapter initializeRestAdapter() {
        Log.i(LOG_TAG, "Rest Adapter Initialise");
        OkHttpClient okHttpClient = new OkHttpClient();
        Gson gson = new GsonBuilder().create();
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(BASE_URL);
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        } else {
            builder.setLogLevel(RestAdapter.LogLevel.NONE);
        }
        builder.setClient(new OkClient(okHttpClient));
        builder.setConverter(new GsonConverter(gson));
        return builder.build();
    }

    public AppService initializeAppService() {
        Log.i(LOG_TAG, "App Service Initialise");
        appService = restAdapter.create(AppService.class);
        return appService;
    }
}
