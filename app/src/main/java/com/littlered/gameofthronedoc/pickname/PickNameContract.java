package com.littlered.gameofthronedoc.pickname;

import com.littlered.gameofthronedoc.bean.NamesEntity;

import java.util.List;

/**
 * author : littleredDLZ
 * date : 2018-12-20 15:00
 */
public interface PickNameContract {

    interface view {

    }

    interface presenter {
        //初始化数据
        List<NamesEntity> initDatas();
    }
}
