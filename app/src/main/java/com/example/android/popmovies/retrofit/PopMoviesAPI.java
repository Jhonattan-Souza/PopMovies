package com.example.android.popmovies.retrofit;

import com.example.android.popmovies.BuildConfig;
import com.example.android.popmovies.models.PopMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PopMoviesAPI {
    String BASE_URL = "http://api.themoviedb.org/3/";
    String IMAGE_URL = "http://image.tmdb.org/t/p/w342/";
    String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    String API_KEY = BuildConfig.OPEN_MOVIE_DB_API_KEY;

    @GET("movie/{order}?api_key=" + API_KEY)
    Call<PopMovies> getAllMoviesByOrder(@Path("order") String order);
}
