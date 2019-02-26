package com.littlered.gameofthronedoc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.littlered.gameofthronedoc.R;
import com.littlered.gameofthronedoc.bean.HousesEntity;

import androidx.recyclerview.widget.RecyclerView;
import me.yokeyword.indexablerv.IndexableAdapter;

public class HouseAdapter extends IndexableAdapter<HousesEntity> {
    private LayoutInflater mInflater;
    public HouseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_index_house, parent, false);
        return new HouseAdapter.IndexVH(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_house, parent, false);
        return new HouseAdapter.ContentVH(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder viewHolder, String indexTitle) {
        HouseAdapter.IndexVH vh = (HouseAdapter.IndexVH) viewHolder;
        vh.tv.setText(indexTitle);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder viewHolder, HousesEntity housesEntity) {
        HouseAdapter.ContentVH vh = (HouseAdapter.ContentVH) viewHolder;
        vh.tv.setText(housesEntity.getName());
    }

    private class IndexVH extends RecyclerView.ViewHolder {
        TextView tv;

        IndexVH(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_house_index);
        }
    }

    /**
     * 内容的viewholder
     */
    private class ContentVH extends RecyclerView.ViewHolder {
        TextView tv;

        ContentVH(View itemView) {
            super(itemView);
            tv =  itemView.findViewById(R.id.tv_house);
        }
    }
}
