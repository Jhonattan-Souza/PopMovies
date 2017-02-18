package com.example.android.popmovies.retrofit;

import android.util.Log;

import com.example.android.popmovies.models.PopMovies;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PopMoviesController implements Callback<PopMovies> {
    private String LOG_TAG = PopMoviesController.class.getSimpleName();

    public void execute(String movieOrder) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PopMoviesAPI.MOVIE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PopMoviesAPI popMoviesAPI = retrofit.create(PopMoviesAPI.class);

        // Get all movies by the parameter passed String. (From Shared Preferences)
        Call<PopMovies> callMovie = popMoviesAPI.getAllMoviesByOrder(movieOrder);

        callMovie.enqueue(this);
    }
    
    @Override
    public void onResponse(Call<PopMovies> call, Response<PopMovies> response) {}

    @Override
    public void onFailure(Call<PopMovies> call, Throwable t) {
        Log.e(LOG_TAG, t.getMessage(), t);
    }
}