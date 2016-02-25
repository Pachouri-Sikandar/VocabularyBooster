package com.vocabularybooster.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.vocabularybooster.R;
import com.vocabularybooster.adapter.VocabListAdapter;
import com.vocabularybooster.model.VocabModel;
import com.vocabularybooster.parser.BaseVocabResponseParser;
import com.vocabularybooster.parser.VocabKeysParser;
import com.vocabularybooster.util.CommonUtils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 26/2/16.
 */
public class MainVocabFragment extends Fragment {
    @InjectView(R.id.recyclerView_vocab_list)
    protected RecyclerView recyclerViewVocabList;
    @InjectView(R.id.progressBar)
    protected ProgressBar progressBar;

    private static String LOG_TAG = MainVocabFragment.class.getName();
    private Context context;
    private VocabListAdapter vocabListAdapter;
    private ArrayList<VocabKeysParser> vocabKeysParserArrayList = new ArrayList<>();

    public MainVocabFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_vocab_fragment, container, false);
        context = getActivity();
        ButterKnife.inject(this, view);
        setAdapter();
        fetchVocabListFromDB();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CommonUtils.CHECK_ACTION_DONE);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);

        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        broadcastManager.unregisterReceiver(broadcastReceiver);
    }

    private void fetchVocabListFromDB() {
        progressBar.setVisibility(View.VISIBLE);
        ArrayList<VocabKeysParser> vocabArrayList = VocabModel.fetchVocabModel();

        if (vocabArrayList.size() != 0 && !vocabArrayList.isEmpty()) {
            Log.i(LOG_TAG, "Show from DB");
            vocabKeysParserArrayList.clear();
            vocabKeysParserArrayList.addAll(vocabArrayList);
            setAdapter();
        } else {
            Log.i(LOG_TAG, "Api Call");
            CommonUtils commonUtils = new CommonUtils();
            commonUtils.getVocabularyApiCall(context);
        }
    }

    public void updateVocabList(ArrayList<VocabKeysParser> vocabArrayList) {
        Log.i(LOG_TAG, "Updating data");
        vocabKeysParserArrayList.clear();
        vocabKeysParserArrayList.addAll(vocabArrayList);
        setAdapter();
    }

    private void setAdapter() {
        Log.i(LOG_TAG, "Set Adapter");
        if (progressBar != null) {
            if (vocabListAdapter != null) {
                vocabListAdapter.notifyDataSetChanged();
            } else {
                LinearLayoutManager manager = new LinearLayoutManager(context);
                recyclerViewVocabList.setLayoutManager(manager);
                vocabListAdapter = new VocabListAdapter(context, vocabKeysParserArrayList);
                recyclerViewVocabList.setAdapter(vocabListAdapter);
            }
            progressBar.setVisibility(View.GONE);
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(LOG_TAG, "Broadcast In");
            Gson gson = new Gson();
            BaseVocabResponseParser baseVocabResponseParser = gson.fromJson(intent.getStringExtra(CommonUtils.CHECK_BROADCAST), BaseVocabResponseParser.class);
            updateVocabList(baseVocabResponseParser.getWords());
        }
    };
}
