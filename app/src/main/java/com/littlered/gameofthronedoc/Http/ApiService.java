package com.littlered.gameofthronedoc.http;

import com.littlered.gameofthronedoc.entity.NamesEntity;
import com.littlered.gameofthronedoc.entity.CulturesEntity;
import com.littlered.gameofthronedoc.entity.HousesEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("characters")
    Observable<List<NamesEntity>> getCharacters();

    @GET("houses")
    Observable<List<HousesEntity>> getHouses();

    @GET("cultures")
    Observable<List<CulturesEntity>> getCultures();
}
