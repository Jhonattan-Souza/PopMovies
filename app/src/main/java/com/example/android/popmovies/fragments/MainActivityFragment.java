package com.example.android.popmovies.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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
import com.example.android.popmovies.models.PopMovies;
import com.example.android.popmovies.retrofit.PopMoviesController;
import com.example.android.popmovies.utilities.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import retrofit2.Call;
import retrofit2.Response;

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

        new PopMoviesController(){
            @Override
            public void onResponse(Call<PopMovies> call, Response<PopMovies> response) {
                mLoadingIndicator.setVisibility(View.INVISIBLE);

                if(response.isSuccessful()) {
                    List<Movie> movies = response.body().getResults();

                    mMovieAdapter.clear();
                    mMovieAdapter.addAll(movies);
                }
            }
        }.execute(movieOrder);
    }
}
