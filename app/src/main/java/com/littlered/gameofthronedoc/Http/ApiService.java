package com.littlered.gameofthronedoc.Http;

import com.littlered.gameofthronedoc.entity.CharactersEntity;
import com.littlered.gameofthronedoc.entity.CulturesEntity;
import com.littlered.gameofthronedoc.entity.HousesEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("characters")
    Observable<List<CharactersEntity>> getCharacters();

    @GET("houses")
    Observable<List<HousesEntity>> getHouses();

    @GET("cultures")
    Observable<List<CulturesEntity>> getCultures();
}
