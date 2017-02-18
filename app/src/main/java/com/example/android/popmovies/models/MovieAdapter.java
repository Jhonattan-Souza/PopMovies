package com.example.android.popmovies.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.android.popmovies.R;
import com.example.android.popmovies.retrofit.PopMoviesAPI;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {
    public MovieAdapter(Context context, List<Movie> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @Nullable ViewGroup parent) {
        Movie movie = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater
                    .from(getContext())
                    .inflate(R.layout.list_item_imageview, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.list_item_image);

        Glide.with(convertView.getContext())
                .load(PopMoviesAPI.IMAGE_BASE_URL + movie.getPoster_path())
                .placeholder(R.mipmap.user_placeholder)
                .error(R.mipmap.user_placeholder_error)
                .into(imageView);

        return convertView;
    }
}