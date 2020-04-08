package com.example.tutapplication;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CoinRetrofit {
    @GET("api/tickers")
        Call<CoinLoreResponse> getCoinData();

}
