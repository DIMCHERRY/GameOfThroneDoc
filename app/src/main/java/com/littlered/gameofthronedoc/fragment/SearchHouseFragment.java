package com.littlered.gameofthronedoc.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.littlered.gameofthronedoc.R;
import com.littlered.gameofthronedoc.entity.HousesEntity;
import com.littlered.gameofthronedoc.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchHouseFragment extends Fragment {
    private SearchHouseFragment.SearchAdapter mAdapter;
    private List<HousesEntity> mDatas;
    private String mQueryText;
    @BindView(R.id.tv_house_no_result)
    TextView mTvNoResult;
    @BindView(R.id.recycler_house)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_house, container, false);
        mTvNoResult =  view.findViewById(R.id.tv_house_no_result);
        mRecyclerView = view.findViewById(R.id.recycler_house);
        ButterKnife.bind(this,view);
        return view;
    }

    /**
     * 数据绑定
     * @param datas
     */
    public void bindDatas(List<HousesEntity> datas) {
        this.mDatas = datas;
        mAdapter = new SearchHouseFragment.SearchAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        if (mQueryText != null) {
            mAdapter.getFilter().filter(mQueryText);
        }
    }

    /**
     * 根据newText 进行查找, 显示
     */
    public void bindQueryText(String newText) {
        if (mDatas == null) {
            mQueryText = newText.toLowerCase();
        } else if (!TextUtils.isEmpty(newText)) {
            mAdapter.getFilter().filter(newText.toLowerCase());
        }
    }

    private class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.VH> implements Filterable {
        private List<HousesEntity> items = new ArrayList<>();

        SearchAdapter() {
            items.clear();
            items.addAll(mDatas);
        }

        @NonNull
        @Override
        public SearchHouseFragment.SearchAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final SearchHouseFragment.SearchAdapter.VH holder = new SearchHouseFragment.SearchAdapter
                    .VH(LayoutInflater.from(getActivity()).inflate(R.layout.item_house, parent, false));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    ToastUtil.showShort(getActivity(), "选择了" + items.get(position).getName());
                }
            });
            return holder;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public void onBindViewHolder(@NonNull SearchHouseFragment.SearchAdapter.VH holder, int position) {
            holder.tvName.setText(items.get(position).getName());
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    ArrayList<HousesEntity> list = new ArrayList<>();
                    for (HousesEntity item : mDatas) {
                        if (item.getPinyin().startsWith(constraint.toString()) || item.getName().contains(constraint)) {
                            list.add(item);
                        }
                    }
                    FilterResults results = new FilterResults();
                    results.count = list.size();
                    results.values = list;
                    return results;
                }

                @Override
                @SuppressWarnings("unchecked")
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    ArrayList<HousesEntity> list = (ArrayList<HousesEntity>) results.values;
                    items.clear();
                    items.addAll(list);
                    if (results.count == 0) {
                        mTvNoResult.setVisibility(View.VISIBLE);
                    } else {
                        mTvNoResult.setVisibility(View.INVISIBLE);
                    }
                    notifyDataSetChanged();
                }
            };
        }

        /**
         * 内容的viewholder 例如家族名字
         */
        class VH extends RecyclerView.ViewHolder {
            private TextView tvName;

            VH(View itemView) {
                super(itemView);
                tvName =  itemView.findViewById(R.id.tv_house);
            }
        }
    }
}
