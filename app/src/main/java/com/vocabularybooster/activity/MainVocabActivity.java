package com.vocabularybooster.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.vocabularybooster.R;
import com.vocabularybooster.fragment.MainVocabFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 26/2/16.
 */
public class MainVocabActivity extends BaseActivity {

    @InjectView(R.id.toolbar_activity)
    protected Toolbar mainToolbar;

    private static String LOG_TAG = MainVocabActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_vocab_activity);
        ButterKnife.inject(this);
        setMainToolbar();
        setVocabFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

    private void setMainToolbar() {
        Log.i(LOG_TAG, "Set Toolbar");
        setSupportActionBar(mainToolbar);
        mainToolbar.setTitle(getString(R.string.app_name));
    }

    private void setVocabFragment() {
        Log.i(LOG_TAG, "Show fragment");
        MainVocabFragment fragment = new MainVocabFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_layout_container, fragment, "fragment");
        transaction.commit();
    }
}
