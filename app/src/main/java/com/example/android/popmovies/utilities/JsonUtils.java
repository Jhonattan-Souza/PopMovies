package com.example.android.popmovies.utilities;

import com.example.android.popmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    /**
     * @param movieJsonStr The Json String that returns from TheOpenMovieDB API Calls.
     * @return A list from the data movies.
     * @throws JSONException Relationed to Bad Json Format.
     */
    public ArrayList<Movie> getMovieDataFromJson(String movieJsonStr) throws JSONException {
        final String MDB_ID = "id";
        final String MDB_RESULTS = "results";
        final String MDB_TITLE = "title";
        final String MDB_POSTER_PATH = "poster_path";
        final String MDB_OVERVIEW = "overview";
        final String MDB_RELEASE_DATE = "release_date";
        final String MDB_VOTE_AVERAGE = "vote_average";

        ArrayList<Movie> listOfMovies = new ArrayList<>();
        Movie movie;

        JSONObject movieJson = new JSONObject(movieJsonStr);
        JSONArray moviesArray = movieJson.getJSONArray(MDB_RESULTS);

        for (int i = 0; i < moviesArray.length(); i++) {
            movie = new Movie();

            JSONObject jsonMovie = moviesArray.getJSONObject(i);

            movie.setId(jsonMovie.getInt(MDB_ID));
            movie.setTitle(jsonMovie.getString(MDB_TITLE));
            movie.setPoster_path(jsonMovie.getString(MDB_POSTER_PATH));
            movie.setOverview(jsonMovie.getString(MDB_OVERVIEW));
            movie.setRelease_date(jsonMovie.getString(MDB_RELEASE_DATE));
            movie.setVote_average(jsonMovie.getDouble(MDB_VOTE_AVERAGE));

            listOfMovies.add(movie);
        }

        return listOfMovies;
    }
}
