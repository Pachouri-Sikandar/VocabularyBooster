package com.vocabularybooster.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vocabularybooster.R;
import com.vocabularybooster.parser.VocabKeysParser;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 26/2/16.
 */
public class VocabListAdapter extends RecyclerView.Adapter<VocabListAdapter.VocabListViewHolder> {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<VocabKeysParser> vocabKeysParserArrayList = new ArrayList<>();

    public VocabListAdapter(Context context, ArrayList<VocabKeysParser> vocabKeysParserArrayList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.vocabKeysParserArrayList = vocabKeysParserArrayList;
    }

    @Override
    public VocabListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_row_vocab_list, parent, false);
        VocabListViewHolder viewHolder = new VocabListViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (vocabKeysParserArrayList != null && !vocabKeysParserArrayList.isEmpty()) {
            count = vocabKeysParserArrayList.size();
        }
        return count;
    }

    @Override
    public void onBindViewHolder(VocabListViewHolder holder, int position) {
        VocabKeysParser vocabObj = vocabKeysParserArrayList.get(position);
        holder.textViewWord.setText(vocabObj.getWord());
        holder.textViewMeaning.setText(vocabObj.getMeaning());
        if (vocabObj.getRatio() >= 0) {
            holder.textViewWord.setVisibility(View.VISIBLE);
            holder.textViewMeaning.setVisibility(View.VISIBLE);
            holder.imageViewMeaning.setVisibility(View.VISIBLE);
            Picasso.with(context).load(context.getString(R.string.image_url, vocabObj.getId
                    ())).placeholder(android.R.drawable.ic_menu_gallery).into(holder
                    .imageViewMeaning);
        } else {
            holder.textViewWord.setVisibility(View.GONE);
            holder.textViewMeaning.setVisibility(View.GONE);
            holder.imageViewMeaning.setVisibility(View.GONE);
        }
    }

    public static class VocabListViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.textView_word)
        protected TextView textViewWord;
        @InjectView(R.id.textView_meaning)
        protected TextView textViewMeaning;
        @InjectView(R.id.imageView_meaning)
        protected ImageView imageViewMeaning;

        public VocabListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }
}