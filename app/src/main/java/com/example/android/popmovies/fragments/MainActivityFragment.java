package com.example.android.popmovies.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.android.popmovies.R;
import com.example.android.popmovies.activities.DetailActivity;
import com.example.android.popmovies.models.Movie;
import com.example.android.popmovies.models.MovieAdapter;
import com.example.android.popmovies.utilities.JsonUtils;
import com.example.android.popmovies.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class MainActivityFragment extends Fragment {
    @BindView(R.id.linear_internet_error) LinearLayout mLinearErrorLayout;
    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadingIndicator;
    @BindView(R.id.gridview_movies) GridView mGridView;

    private MovieAdapter mMovieAdapter;

    public MainActivityFragment() {}

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);

        setGridAdapter();

        return rootView;
    }

    /**
     * GridView initial adapter configurations.
     */
    private void setGridAdapter(){
        // Initialize a empty adapter to GridView.
        mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());
        mGridView.setAdapter(mMovieAdapter);
    }

    /**
     * Calls the DetailView.
     * @param position The position of current movie selected in the Grid.
     */
    @OnItemClick(R.id.gridview_movies)
    public void onGridItemSelected(int position){
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(getString(R.string.movie_object_key), mMovieAdapter.getItem(position));
        startActivity(intent);
    }

    /**
     * Set the button click listener.
     */
    @OnClick(R.id.bt_internet_error)
    public void onInternetErrorButtonClick(){
        updateMovies();
    }

    /**
     * Updates the GridView if the device has internet available, otherwise show the error Layout.
     */
    private void updateMovies() {
        mLoadingIndicator.setVisibility(View.VISIBLE);

        if (NetworkUtils.isNetworkAvailable(getContext())) {
            executeMovieTask();
            mLinearErrorLayout.setVisibility(View.GONE);
        } else {
            mLinearErrorLayout.setVisibility(View.VISIBLE);
            mLoadingIndicator.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Take the order according to the preference and execute the task.
     */
    private void executeMovieTask() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String movieOrder = prefs.getString(getString(R.string.pref_order_list_key), getString(R.string.pref_top_rated_key));

        new FetchMovieTask().execute(movieOrder);
    }

    /**
     * Class responsible for running the task in the background.
     */
    public class FetchMovieTask extends AsyncTask<String, Object, ArrayList<Movie>> {
        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            String movieJsonStr;

            try {
                // Build the url with the order parameter.
                URL url = NetworkUtils.buildUrl(strings[0]);

                // Get response from HTTP.
                movieJsonStr = NetworkUtils.getResponseFromHttpUrl(url);

                // Returns the list of movies.
                return new JsonUtils().getMovieDataFromJson(movieJsonStr);

            } catch (JSONException|IOException ex) {
                Log.e(LOG_TAG, ex.getMessage(), ex);
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movies != null) {
                mMovieAdapter.clear();
                for (Movie movie : movies) {
                    mMovieAdapter.add(movie);
                }
            }
        }
    }

}
