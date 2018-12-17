package com.littlered.gameofthronedoc;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;
import com.littlered.gameofthronedoc.Http.ApiMethods;
import com.littlered.gameofthronedoc.adapter.HouseAdapter;
import com.littlered.gameofthronedoc.entity.HousesEntity;
import com.littlered.gameofthronedoc.fragment.SearchHouseFragment;
import com.littlered.gameofthronedoc.observer.ObserverOnNextListener;
import com.littlered.gameofthronedoc.progress.ProgressObserver;
import com.littlered.gameofthronedoc.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import me.yokeyword.indexablerv.EntityWrapper;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import me.yokeyword.indexablerv.SimpleHeaderAdapter;

public class PickHouseActivity extends AppCompatActivity {
    private SearchHouseFragment mSearchFragment;
    private SearchView mSearchView;
    private FrameLayout mProgressBar;
    private HouseAdapter adapter;

    private List<HousesEntity> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_house);

        mSearchFragment = (SearchHouseFragment) getSupportFragmentManager().findFragmentById(R.id.search_house_fragment);
        IndexableLayout indexableLayout = findViewById(R.id.indexableLayout);
        mSearchView = findViewById(R.id.search_house_view);
        mProgressBar = findViewById(R.id.progress);

        indexableLayout.setLayoutManager(new GridLayoutManager(this, 2));

        // 多音字处理
        Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(this)));

        // 快速排序。  排序规则设置为：只按首字母  （默认全拼音排序）  效率很高，是默认的10倍左右。
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);

        // setAdapter
        adapter = new HouseAdapter(this);
        indexableLayout.setAdapter(adapter);
        // set Datas
        mDatas = initDatas();

        adapter.setDatas(mDatas, new IndexableAdapter.IndexCallback<HousesEntity>() {
            @Override
            public void onFinished(List<EntityWrapper<HousesEntity>> datas) {
                // 数据处理完成后回调
                mSearchFragment.bindDatas(mDatas);
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
                    ToastUtil.showShort(PickHouseActivity.this, "选中:" + entity.getName() +
                            "  当前位置:" + currentPosition + "  原始所在数组位置:" + originalPosition);
                } else {
                    ToastUtil.showShort(PickHouseActivity.this, "选中Header:" + entity.getName() + "  当前位置:" + currentPosition);
                }
            }
        });

        adapter.setOnItemTitleClickListener(new IndexableAdapter.OnItemTitleClickListener() {
            @Override
            public void onItemClick(View v, int currentPosition, String indexTitle) {
                ToastUtil.showShort(PickHouseActivity.this, "选中:" + indexTitle + "  当前位置:" + currentPosition);
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
                    HousesEntity cityEntity = new HousesEntity();
                    cityEntity.setName(item);
                    list.add(cityEntity);
                }
                adapter.notifyDataSetChanged();
            }
        };
        ApiMethods.getHouses(new ProgressObserver<List<HousesEntity>>(this, listener));

        return list;
    }

    private List<HousesEntity> iniyHotCityDatas() {
        List<HousesEntity> list = new ArrayList<>();
        list.add(new HousesEntity("杭州市"));
        list.add(new HousesEntity("北京市"));
        list.add(new HousesEntity("上海市"));
        list.add(new HousesEntity("广州市"));
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
