package com.littlered.gameofthronedoc.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;
import com.littlered.gameofthronedoc.PickNameActivity;
import com.littlered.gameofthronedoc.R;
import com.littlered.gameofthronedoc.adapter.NameAdapter;
import com.littlered.gameofthronedoc.entity.NamesEntity;
import com.littlered.gameofthronedoc.http.ApiMethods;
import com.littlered.gameofthronedoc.observer.ObserverOnNextListener;
import com.littlered.gameofthronedoc.progress.ProgressObserver;
import com.littlered.gameofthronedoc.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    private List<NamesEntity> iniyHotCityDatas() {
        List<NamesEntity> list = new ArrayList<>();
        list.add(new NamesEntity("Arya Stark"));
        list.add(new NamesEntity("Jaime Lannister"));
        list.add(new NamesEntity("Daenerys Targaryen"));
        return list;
    }

}
