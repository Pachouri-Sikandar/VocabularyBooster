package com.vocabularybooster.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.vocabularybooster.BaseApplication;
import com.vocabularybooster.R;
import com.vocabularybooster.service.AppService;

/**
 * Created by ankit on 26/2/16.
 */
public class BaseActivity extends AppCompatActivity {

    private static String LOG_TAG = BaseActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public AppService getService() {
        AppService service = ((BaseApplication) getApplicationContext()).appService;
        if (service == null) {
            service = ((BaseApplication) getApplicationContext()).initializeAppService();
        }
        return service;
    }

    public void showSnackBar(Context context, View view, String message) {
        Log.i(LOG_TAG, "Show snack bar");
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        View rootView = snackbar.getView();
        rootView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        TextView textViewSnackBar = (TextView) rootView.findViewById(android.support.design.R.id.snackbar_text);
        textViewSnackBar.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
