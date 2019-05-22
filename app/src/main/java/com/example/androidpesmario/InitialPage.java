package com.example.androidpesmario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InitialPage extends AppCompatActivity{
    List<Partido> listDatos = new ArrayList<>();
    RecyclerView recycler;
    RecyclerView.Adapter mAdapter;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.43.63:9000/ApplicationAndroid/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    API api = retrofit.create(API.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initialpage);

        recycler = (RecyclerView)findViewById(R.id.my_recycler_view);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(true);
        Call<List<Partido>> call = api.getPartidos();
        call.enqueue(new Callback<List<Partido>>() {
            @Override
            public void onResponse(Call<List<Partido>> call, Response<List<Partido>> response) {
                if (!response.isSuccessful()) {
                    listDatos = response.body();
                }
                listDatos = response.body();
                mAdapter = new MyAdapter(listDatos, InitialPage.this);
                recycler.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Partido>> call, Throwable t){
            }

        });
    }

}
