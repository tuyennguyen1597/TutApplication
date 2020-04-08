package com.example.tutapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private boolean mTwoPane;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
        }
        mRecyclerView = findViewById(R.id.rvList);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coinlore.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoinRetrofit coinRetrofit = retrofit.create(CoinRetrofit.class);

        Call<CoinLoreResponse> coinsCall = coinRetrofit.getCoinData();
        coinsCall.enqueue(new Callback<CoinLoreResponse>() {
            @Override
            public void onResponse(Call<CoinLoreResponse> call, Response<CoinLoreResponse> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Do Response code here");
                    List<Coin> coins = response.body().getData();
                    RecyclerView.Adapter mAdapter = new CoinAdapter(MainActivity.this, coins, mTwoPane);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    Log.d(TAG, "onResponse: ERROR is " + response.errorBody());
                }
            }
            @Override
            public void onFailure(Call<CoinLoreResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: ON FAILURE IS: " + t.getLocalizedMessage());
            }
        });

        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);



//        Gson gson = new Gson();
//        CoinLoreResponse response = gson.fromJson(CoinLoreResponse.json, CoinLoreResponse.class);
//        List<Coin> coins = response.getData();

//        RecyclerView.Adapter mAdapter = new CoinAdapter(this, coins, mTwoPane);
//        mRecyclerView.setAdapter(mAdapter);
    }
}
