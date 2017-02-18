package com.example.android.popmovies.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import com.example.android.popmovies.BuildConfig;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private final static String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";
    private final static String API_KEY_PARAM = "api_key";

    public final static String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    /**
     * Builds the URL used to query TheOpenMovieDB.
     *
     * @param movieDbSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the TheOpenMovieDB.
     */
    public static URL buildUrl(String movieDbSearchQuery) {
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendPath(movieDbSearchQuery)
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.OPEN_MOVIE_DB_API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, e.getMessage());
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    /**
     * This method checks for internet availability.
     *
     * @param context The caller activity context.
     * @return If internet is available or not.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
