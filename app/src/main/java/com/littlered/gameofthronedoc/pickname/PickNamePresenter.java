package com.littlered.gameofthronedoc.pickname;

import com.littlered.gameofthronedoc.bean.NamesEntity;
import com.littlered.gameofthronedoc.observer.ObserverOnNextListener;

import java.util.ArrayList;
import java.util.List;

/**
 * author : littleredDLZ
 * date : 2018-12-20 15:05
 */
public class PickNamePresenter implements PickNameContract.presenter {
    @Override
    public List<NamesEntity> initDatas() {
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
            }
        };
        return list;
    }
}
