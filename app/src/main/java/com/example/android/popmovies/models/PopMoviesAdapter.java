package com.example.android.popmovies.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popmovies.R;
import com.example.android.popmovies.retrofit.PopMoviesAPI;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopMoviesAdapter extends RecyclerView.Adapter<PopMoviesAdapter.PopMoviesAdapterViewHolder> {

    final private ListItemClickListener mOnClickListener;

    private List<Movie> mMovieList;
    private Context mContext;

    public PopMoviesAdapter(List<Movie> listOfMovies, ListItemClickListener listener){
        mMovieList = listOfMovies;
        mOnClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    @Override
    public PopMoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();

        int layoutIdForListItem = R.layout.list_item_imageview;

        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new PopMoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PopMoviesAdapterViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(PopMoviesAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    class PopMoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView listItemImageView;

        PopMoviesAdapterViewHolder(View view){
            super(view);
            view.setOnClickListener(this);

            listItemImageView = (ImageView) view.findViewById(R.id.list_item_image);
        }

        void bind(int listIndex){
            Movie movie = mMovieList.get(listIndex) ;

            Picasso.with(mContext)
                    .load(PopMoviesAPI.IMAGE_BASE_URL + movie.getPoster_path())
                    .placeholder(R.mipmap.user_placeholder)
                    .error(R.mipmap.user_placeholder_error)
                    .into(listItemImageView);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
