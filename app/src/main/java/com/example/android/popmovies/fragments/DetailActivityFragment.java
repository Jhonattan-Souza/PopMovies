package com.example.android.popmovies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popmovies.R;
import com.example.android.popmovies.models.Movie;
import com.example.android.popmovies.retrofit.PopMoviesAPI;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivityFragment extends Fragment {
    @BindView(R.id.tv_movie_title) TextView mMovieTitleTextView;
    @BindView(R.id.tv_movie_release_date) TextView mReleaseDateTextView;
    @BindView(R.id.tv_movie_vote_average) TextView mVoteAverageTextView;
    @BindView(R.id.tv_movie_overview) TextView mOverviewTextView;

    @BindView(R.id.movie_imageview) ImageView mMovieImageView;

    private Movie movie;
    private View mViewRoot;

    public DetailActivityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mViewRoot = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, mViewRoot);

        getParcelableFromMainActivity();
        setObjectsValues();

        return mViewRoot;
    }

    /**
     * Brings the clicked object info to detail screen.
     */
    private void getParcelableFromMainActivity(){
        Intent i = getActivity().getIntent();
        movie = i.getParcelableExtra(getString(R.string.movie_object_key));
    }

    /**
     * Fragment objects references.
     */
    private void setObjectsValues(){
        // Picasso loads the cache image from the Main View.
        Picasso.with(mViewRoot.getContext())
                .load(PopMoviesAPI.IMAGE_BASE_URL + movie.getPoster_path())
                .placeholder(R.mipmap.user_placeholder)
                .error(R.mipmap.user_placeholder_error)
                .into(mMovieImageView);

        mMovieTitleTextView.setText(movie.getTitle());
        mReleaseDateTextView.setText(movie.getRelease_date());
        mOverviewTextView.setText(movie.getOverview());

        String voteAverageText = String.valueOf(movie.getVote_average()) + getString(R.string.movie_rate_range);
        mVoteAverageTextView.setText(voteAverageText);
    }
}
