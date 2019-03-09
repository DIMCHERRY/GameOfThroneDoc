package com.littlered.gameofthronedoc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlered.gameofthronedoc.R;
import com.littlered.gameofthronedoc.entity.CulturesEntity;

import androidx.recyclerview.widget.RecyclerView;
import me.yokeyword.indexablerv.IndexableAdapter;

public class CultureAdapter extends IndexableAdapter<CulturesEntity> {
    private LayoutInflater mInflater;
    public CultureAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_culture, parent, false);
        return new CultureAdapter.IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_culture, parent, false);
        return new CultureAdapter.ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder viewHolder, String indexTitle) {
        CultureAdapter.IndexVH vh = (CultureAdapter.IndexVH) viewHolder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, CulturesEntity culturesEntity) {
        CultureAdapter.ContentVH vh = (CultureAdapter.ContentVH) viewHolder;
        vh.tv.setText(culturesEntity.getName());
    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        IndexVH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_culture_index);
        }
    }

    /**
     * 内容的viewholder 例如家族名字
     */
    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tv;

        ContentVH(View itemView) {
            super(itemView);
            tv =  itemView.findViewById(R.id.tv_culture);
        }
    }
}
