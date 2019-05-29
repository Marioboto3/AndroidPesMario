package com.example.androidpesmario;

import java.util.List;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface API {
    @GET("getMatchDay")
    Call<List<Partido>> getPartidos();

    @POST("createBet")
    Call<String> createBet(@Body Apuesta apuesta);
}
