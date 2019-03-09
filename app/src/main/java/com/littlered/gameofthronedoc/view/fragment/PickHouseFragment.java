package com.littlered.gameofthronedoc.view.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;
import com.littlered.gameofthronedoc.R;
import com.littlered.gameofthronedoc.adapter.HouseAdapter;
import com.littlered.gameofthronedoc.entity.HousesEntity;
import com.littlered.gameofthronedoc.http.ApiMethods;
import com.littlered.gameofthronedoc.progress.ObserverOnNextListener;
import com.littlered.gameofthronedoc.progress.ProgressObserver;
import com.littlered.gameofthronedoc.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.indexablerv.EntityWrapper;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

public class PickHouseFragment extends Fragment {
    @BindView(R.id.progress_house)
    FrameLayout mProgressBar;
    @BindView(R.id.indexableLayout)
    IndexableLayout indexableLayout;

    private List<HousesEntity> mDatas;
    private HouseAdapter adapter;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_house, container, false);
        ButterKnife.bind(this, view);

        indexableLayout.setLayoutManager(new GridLayoutManager(getContext(), 1));
        // 多音字处理
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(getContext())));

        // 快速排序。  排序规则设置为：只按首字母  （默认全拼音排序）  效率很高，是默认的10倍左右。
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);

        // setAdapter
        adapter = new HouseAdapter(getContext());
        indexableLayout.setAdapter(adapter);
        // set Datas
        mDatas = initDatas();

        adapter.setDatas(mDatas, new IndexableAdapter.IndexCallback<HousesEntity>() {
            @Override
            public void onFinished(List<EntityWrapper<HousesEntity>> datas) {
                // 数据处理完成后回调
                /*                mSearchFragment.bindDatas(mDatas);*/
                mProgressBar.setVisibility(View.GONE);
            }
        });

        // set Center OverlayView
        indexableLayout.setOverlayStyle_Center();
        // set Listener
        adapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<HousesEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, HousesEntity entity) {
                if (originalPosition >= 0) {
                    ToastUtil.showShort(getContext(), "选中:" + entity.getName() + "  当前位置:" + currentPosition + "  原始所在数组位置:" + originalPosition);
                } else {
                    ToastUtil.showShort(getContext(), "选中Header:" + entity.getName() + "  当前位置:" + currentPosition);
                }
            }
        });

        adapter.setOnItemTitleClickListener(new IndexableAdapter.OnItemTitleClickListener() {
            @Override
            public void onItemClick(View v, int currentPosition, String indexTitle) {
                ToastUtil.showShort(getContext(), "选中:" + indexTitle + "  当前位置:" + currentPosition);
            }
        });
        
        initview();
        return view;
    }

    private List<HousesEntity> initDatas() {
        final List<HousesEntity> list = new ArrayList<>();
        final List<String> data = new ArrayList<>();
        final ObserverOnNextListener<List<HousesEntity>> listener = new ObserverOnNextListener<List<HousesEntity>>() {
            @Override
            public void onNext(List<HousesEntity> movie) {
                for (int i = 0; i < movie.size(); i++) {
                    String str = movie.get(i).getName();
                    String toDelete = "House ";
                    str = str.substring(toDelete.length());
                    data.add(str);
                }
                for (String item : data) {
                    HousesEntity HousesEntity = new HousesEntity();
                    HousesEntity.setName(item);
                    list.add(HousesEntity);
                }
                adapter.notifyDataSetChanged();
            }
        };
        ApiMethods.getHouses(new ProgressObserver<List<HousesEntity>>(getContext(), listener));

        return list;
    }

    private void initview() {
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar_main);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("选择家族");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_menu_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
}
