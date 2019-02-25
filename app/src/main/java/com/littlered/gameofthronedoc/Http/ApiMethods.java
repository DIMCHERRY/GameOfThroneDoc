package com.littlered.gameofthronedoc.http;

import com.littlered.gameofthronedoc.bean.NamesEntity;
import com.littlered.gameofthronedoc.bean.CulturesEntity;
import com.littlered.gameofthronedoc.bean.HousesEntity;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApiMethods {

    /**
     * 封装线程管理和订阅的过程
     */
    private static void ApiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 用于获取角色的数据
     * @param observer 由调用者传过来的观察者对象
     * 观察者/被观察者  Observer/Observable
     */
    public static void getCharacters(Observer<List<NamesEntity>> observer) {
        ApiSubscribe(ApiStrategy.getApiService().getCharacters(), observer);
    }

    /**
     * 用于获取家族的数据
     * @param observer
     */
    public static void getHouses(Observer<List<HousesEntity>> observer) {
        ApiSubscribe(ApiStrategy.getApiService().getHouses(), observer);
    }

    /**
     * 用于获取文化的数据
     * @param observer
     */
    public static void getCultures(Observer<List<CulturesEntity>> observer) {
        ApiSubscribe(ApiStrategy.getApiService().getCultures(), observer);
    }
}
