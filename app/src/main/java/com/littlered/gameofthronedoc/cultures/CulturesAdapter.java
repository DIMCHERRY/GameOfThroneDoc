package com.littlered.gameofthronedoc.cultures;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlered.gameofthronedoc.R;
import com.littlered.gameofthronedoc.bean.CulturesEntity;

import androidx.recyclerview.widget.RecyclerView;
import me.yokeyword.indexablerv.IndexableAdapter;

public class CulturesAdapter extends IndexableAdapter<CulturesEntity> {
    private LayoutInflater mInflater;
    public CulturesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_culture, parent, false);
        return new CulturesAdapter.IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_culture, parent, false);
        return new CulturesAdapter.ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder viewHolder, String indexTitle) {
        CulturesAdapter.IndexVH vh = (CulturesAdapter.IndexVH) viewHolder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, CulturesEntity culturesEntity) {
        CulturesAdapter.ContentVH vh = (CulturesAdapter.ContentVH) viewHolder;
        vh.tv.setText(culturesEntity.getName());
    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        IndexVH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_culture_index);
        }
    }

    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tv;

        ContentVH(View itemView) {
            super(itemView);
            tv =  itemView.findViewById(R.id.tv_culture);
        }
    }
}
