package com.littlered.gameofthronedoc;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;


import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;
import com.littlered.gameofthronedoc.Http.ApiMethods;
import com.littlered.gameofthronedoc.adapter.CityAdapter;
import com.littlered.gameofthronedoc.entity.CharactersEntity;
import com.littlered.gameofthronedoc.fragment.SearchFragment;
import com.littlered.gameofthronedoc.observer.ObserverOnNextListener;
import com.littlered.gameofthronedoc.progress.ProgressObserver;
import com.littlered.gameofthronedoc.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import me.yokeyword.indexablerv.EntityWrapper;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import me.yokeyword.indexablerv.SimpleHeaderAdapter;

/**
 * 选择角色
 */
public class PickCityActivity extends AppCompatActivity {
    private List<CharactersEntity> mDatas;
    private SearchFragment mSearchFragment;
    private SearchView mSearchView;
    private FrameLayout mProgressBar;
    private CityAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_city);

        mSearchFragment = (SearchFragment) getSupportFragmentManager().findFragmentById(R.id.search_fragment);
        IndexableLayout indexableLayout =  findViewById(R.id.indexableLayout);
        mSearchView =  findViewById(R.id.searchview);
        mProgressBar =  findViewById(R.id.progress);


        indexableLayout.setLayoutManager(new GridLayoutManager(this, 2));

        // 多音字处理
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(this)));

        // 快速排序。  排序规则设置为：只按首字母  （默认全拼音排序）  效率很高，是默认的10倍左右。
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);

        // setAdapter
        adapter = new CityAdapter(this);
        indexableLayout.setAdapter(adapter);
        // set Datas
        mDatas = initDatas();

        adapter.setDatas(mDatas, new IndexableAdapter.IndexCallback<CharactersEntity>() {
            @Override
            public void onFinished(List<EntityWrapper<CharactersEntity>> datas) {
                // 数据处理完成后回调
                mSearchFragment.bindDatas(mDatas);
                mProgressBar.setVisibility(View.GONE);
            }
        });

        // set Center OverlayView
        indexableLayout.setOverlayStyle_Center();

        // set Listener
        adapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<CharactersEntity>() {
            @Override
            public void onItemClick(View v, int originalPosition, int currentPosition, CharactersEntity entity) {
                if (originalPosition >= 0) {
                    ToastUtil.showShort(PickCityActivity.this, "选中:" + entity.getName() + "  当前位置:" + currentPosition + "  原始所在数组位置:" + originalPosition);
                } else {
                    ToastUtil.showShort(PickCityActivity.this, "选中Header:" + entity.getName() + "  当前位置:" + currentPosition);
                }
            }
        });

        adapter.setOnItemTitleClickListener(new IndexableAdapter.OnItemTitleClickListener() {
            @Override
            public void onItemClick(View v, int currentPosition, String indexTitle) {
                ToastUtil.showShort(PickCityActivity.this, "选中:" + indexTitle + "  当前位置:" + currentPosition);
            }
        });

        // 添加 HeaderView DefaultHeaderAdapter接收一个IndexableAdapter, 使其布局以及点击事件和IndexableAdapter一致
        // 如果想自定义布局,点击事件, 可传入 new IndexableHeaderAdapter
        SimpleHeaderAdapter mHotCityAdapter = new SimpleHeaderAdapter<>(adapter, "热", "热门城市", iniyHotCityDatas());
        // 热门城市
        indexableLayout.addHeaderAdapter(mHotCityAdapter);
        // 搜索Demo
        initSearch();
    }

    private List<CharactersEntity> initDatas() {
        final List<CharactersEntity> list = new ArrayList<>();
        final List<String> data = new ArrayList<>();
        final ObserverOnNextListener<List<CharactersEntity>> listener = new ObserverOnNextListener<List<CharactersEntity>>() {
            @Override
            public void onNext(List<CharactersEntity> movie) {
                for (int i = 0; i < movie.size(); i++) {
                    data.add(movie.get(i).getName());
                }
                for (String item : data) {
                    CharactersEntity cityEntity = new CharactersEntity();
                    cityEntity.setName(item);
                    list.add(cityEntity);
                }
                adapter.notifyDataSetChanged();
            }
        };
        ApiMethods.getCharacters(new ProgressObserver<List<CharactersEntity>>(this, listener));

        return list;
    }

    private List<CharactersEntity> iniyHotCityDatas() {
        List<CharactersEntity> list = new ArrayList<>();
        list.add(new CharactersEntity("杭州市"));
        list.add(new CharactersEntity("北京市"));
        list.add(new CharactersEntity("上海市"));
        list.add(new CharactersEntity("广州市"));
        return list;
    }

    private void initSearch() {
        getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0) {
                    if (mSearchFragment.isHidden()) {
                        getSupportFragmentManager().beginTransaction().show(mSearchFragment).commit();
                    }
                } else {
                    if (!mSearchFragment.isHidden()) {
                        getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();
                    }
                }

                mSearchFragment.bindQueryText(newText);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!mSearchFragment.isHidden()) {
            // 隐藏 搜索
            mSearchView.setQuery(null, false);
            getSupportFragmentManager().beginTransaction().hide(mSearchFragment).commit();
            return;
        }
        super.onBackPressed();
    }
}
