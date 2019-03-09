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
import com.littlered.gameofthronedoc.entity.CulturesEntity;
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

public class SearchCulturesFragment extends Fragment {
    private SearchCulturesFragment.SearchAdapter mAdapter;
    private List<CulturesEntity> mDatas;
    private String mQueryText;
    @BindView(R.id.tv_culture_no_result)
    TextView mTvNoResult;
    @BindView(R.id.recycler_culture)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_culture, container, false);
        mTvNoResult = (TextView) view.findViewById(R.id.tv_no_result);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recy);
        ButterKnife.bind(this,view);
        return view;
    }

    /**
     * 数据绑定
     * @param datas
     */
    public void bindDatas(List<CulturesEntity> datas) {
        this.mDatas = datas;
        mAdapter = new SearchCulturesFragment.SearchAdapter();
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
        private List<CulturesEntity> items = new ArrayList<>();

        SearchAdapter() {
            items.clear();
            items.addAll(mDatas);
        }

        @NonNull
        @Override
        public SearchCulturesFragment.SearchAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final SearchCulturesFragment.SearchAdapter.VH holder = new SearchCulturesFragment.SearchAdapter.VH(LayoutInflater.from(getActivity()).inflate(R.layout.item_culture, parent, false));
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
        public void onBindViewHolder(SearchCulturesFragment.SearchAdapter.VH holder, int position) {
            holder.tvName.setText(items.get(position).getName());
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    ArrayList<CulturesEntity> list = new ArrayList<>();
                    for (CulturesEntity item : mDatas) {
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
                    ArrayList<CulturesEntity> list = (ArrayList<CulturesEntity>) results.values;
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

        class VH extends RecyclerView.ViewHolder {
            private TextView tvName;

            VH(View itemView) {
                super(itemView);
                tvName =  itemView.findViewById(R.id.tv_culture);
            }
        }
    }
}
