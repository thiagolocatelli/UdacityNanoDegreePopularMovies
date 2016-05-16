package com.github.thiagolocatelli.popularmovies.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;
import com.bumptech.glide.Glide;
import com.github.thiagolocatelli.popularmovies.app.R;
import com.github.thiagolocatelli.popularmovies.app.parcel.MovieParcel;
import com.github.thiagolocatelli.popularmovies.app.util.PosterUtility;
import com.github.thiagolocatelli.popularmovies.app.viewholder.MovieViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thiago on 4/20/16.
 */
public class MoviesAdapter  extends RecyclerView.Adapter<MovieViewHolder> {

    public interface OnMovieSelectedListener {
        void onMovieListSelected(MovieParcel movie);
    }

    private List<MovieParcel> mMovieList;
    private LayoutInflater mInflater;
    private Context mContext;
    private OnMovieSelectedListener mOnMovieSelectedListener;

    public MoviesAdapter(Context context, OnMovieSelectedListener onMovieSelectedListener) {
        this.mContext = context;
        this.mOnMovieSelectedListener = onMovieSelectedListener;
        this.mInflater = LayoutInflater.from(context);
        this.mMovieList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return (mMovieList == null) ? 0 : mMovieList.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_movie, parent, false);
        final MovieViewHolder viewHolder = new MovieViewHolder(view);

        viewHolder.rippleView.setFrameRate(10);
        viewHolder.rippleView.setRippleDuration(200);
        viewHolder.rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                if(mOnMovieSelectedListener != null){
                    int position = viewHolder.getAdapterPosition();
                    mOnMovieSelectedListener.onMovieListSelected(mMovieList.get(position));
                }
            }

        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MovieParcel movie = mMovieList.get(position);

        Glide.with(mContext)
                .load(PosterUtility.getFullPosterPath342(movie.getPosterPath()))
                .placeholder(R.drawable.movie_placeholder)
                .fitCenter()
                .into(holder.imageView);
    }

    public void setMovieList(List<MovieParcel> movieList) {
        this.mMovieList.clear();
        this.mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public void clear() {
        this.mMovieList.clear();
        notifyDataSetChanged();
    }
}
