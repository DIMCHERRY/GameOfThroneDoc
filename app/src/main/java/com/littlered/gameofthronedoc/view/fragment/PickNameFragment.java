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
import com.littlered.gameofthronedoc.adapter.NameAdapter;
import com.littlered.gameofthronedoc.bean.NamesEntity;
import com.littlered.gameofthronedoc.http.ApiMethods;
import com.littlered.gameofthronedoc.observer.ObserverOnNextListener;
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
import me.yokeyword.indexablerv.SimpleHeaderAdapter;

/**
 * author : littleredDLZ
 * date : 2018-12-20 11:00
 */
public class PickNameFragment extends Fragment {
    @BindView(R.id.progress_name)
    FrameLayout mProgressBar;
    @BindView(R.id.indexableLayout)
    IndexableLayout indexableLayout;

    private List<NamesEntity> mDatas;
    private NameAdapter adapter;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_name, container, false);
        ButterKnife.bind(this, view);

        indexableLayout.setLayoutManager(new GridLayoutManager(getContext(), 1));
        // 多音字处理
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(getContext())));

        // 快速排序。  排序规则设置为：只按首字母  （默认全拼音排序）  效率很高，是默认的10倍左右。
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);

        // setAdapter
        adapter = new NameAdapter(getContext());
        indexableLayout.setAdapter(adapter);
        // set Datas
        mDatas = initDatas();

        adapter.setDatas(mDatas, new IndexableAdapter.IndexCallback<NamesEntity>() {
            @Override
            public void onFinished(List<EntityWrapper<NamesEntity>> datas) {
                // 数据处理完成后回调
                /*                mSearchFragment.bindDatas(mDatas);*/
                mProgressBar.setVisibility(View.GONE);
            }
        });

        // set Center OverlayView
        indexableLayout.setOverlayStyle_Center();
        // set Listener
        adapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<NamesEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, NamesEntity entity) {
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

        // 添加 HeaderView DefaultHeaderAdapter接收一个IndexableAdapter, 使其布局以及点击事件和IndexableAdapter一致
        // 如果想自定义布局,点击事件, 可传入 new IndexableHeaderAdapter
        SimpleHeaderAdapter mHotCityAdapter = new SimpleHeaderAdapter<>(adapter, "热", "热门角色", iniyHotCityDatas());
        // 热门角色
        indexableLayout.addHeaderAdapter(mHotCityAdapter);
        initview();
        return view;
    }

    private List<NamesEntity> initDatas() {
        final List<NamesEntity> list = new ArrayList<>();
        final List<String> data = new ArrayList<>();
        final ObserverOnNextListener<List<NamesEntity>> listener = new ObserverOnNextListener<List<NamesEntity>>() {
            @Override
            public void onNext(List<NamesEntity> movie) {
                for (int i = 0; i < movie.size(); i++) {
                    data.add(movie.get(i).getName());
                }
                for (String item : data) {
                    NamesEntity namesEntity = new NamesEntity();
                    namesEntity.setName(item);
                    list.add(namesEntity);
                }
                adapter.notifyDataSetChanged();
            }
        };
        ApiMethods.getCharacters(new ProgressObserver<List<NamesEntity>>(getContext(), listener));

        return list;
    }

    private void initview() {
        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar_main);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("选择角色");
    }

    private List<NamesEntity> iniyHotCityDatas() {
        List<NamesEntity> list = new ArrayList<>();
        list.add(new NamesEntity("Arya Stark"));
        list.add(new NamesEntity("Jaime Lannister"));
        list.add(new NamesEntity("Daenerys Targaryen"));
        return list;
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


