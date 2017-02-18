package com.example.android.popmovies.retrofit;

import android.util.Log;

import com.example.android.popmovies.models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopMoviesController implements Callback<PopMovies> {

    public void start() {
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PopMoviesAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PopMoviesAPI popMoviesAPI = retrofit.create(PopMoviesAPI.class);

        Call<PopMovies> callMovie = popMoviesAPI.getPopMovies();

        callMovie.enqueue(this);
    }

    @Override
    public void onResponse(Call<PopMovies> call, Response<PopMovies> response) {
        if(response.isSuccessful()) {
            List<com.example.android.popmovies.models.Movie> changesList = response.body().getResults();

            for (com.example.android.popmovies.models.Movie movie : changesList) {
                System.out.println(movie.getTitle());
            }


        } else {
            System.out.println(response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<PopMovies> call, Throwable t) {
        Log.e("MovieTest", t.getMessage(), t);
    }
}