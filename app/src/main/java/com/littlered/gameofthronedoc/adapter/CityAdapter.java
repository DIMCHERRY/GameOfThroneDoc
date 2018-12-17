package com.littlered.gameofthronedoc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlered.gameofthronedoc.R;
import com.littlered.gameofthronedoc.entity.CharactersEntity;

import androidx.recyclerview.widget.RecyclerView;
import me.yokeyword.indexablerv.IndexableAdapter;

public class CityAdapter extends IndexableAdapter<CharactersEntity> {
    private LayoutInflater mInflater;

    public CityAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_city, parent, false);
        return new IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_city, parent, false);
        return new ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        IndexVH vh = (IndexVH) holder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, CharactersEntity entity) {
        ContentVH vh = (ContentVH) holder;
        vh.tv.setText(entity.getName());
    }

    /**
     * 索引字母的viewholder
     */
    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        IndexVH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_index);
        }
    }

    /**
     * 内容的viewholder 例如角色名字
     */
    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tv;

        ContentVH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_name);
        }
    }
}
