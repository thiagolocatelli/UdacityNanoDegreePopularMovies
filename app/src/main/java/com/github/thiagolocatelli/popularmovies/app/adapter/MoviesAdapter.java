package com.github.thiagolocatelli.popularmovies.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;
import com.github.thiagolocatelli.moviedb.model.Movie;
import com.github.thiagolocatelli.popularmovies.app.activity.MovieDetailsActivity;
import com.github.thiagolocatelli.popularmovies.app.parcel.MovieParcel;
import com.github.thiagolocatelli.popularmovies.app.util.Constants;
import com.github.thiagolocatelli.popularmovies.app.viewholder.MovieViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.github.thiagolocatelli.popularmovies.app.R;

import com.github.thiagolocatelli.popularmovies.app.util.PosterUtility;

/**
 * Created by thiago on 4/20/16.
 */
public class MoviesAdapter  extends RecyclerView.Adapter<MovieViewHolder> {

    private List<Movie> mMovieList;
    private LayoutInflater mInflater;
    private Context mContext;

    public MoviesAdapter(Context context) {
        this.mContext = context;
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

        //RippleView ripple = view.find
        viewHolder.rippleView.setFrameRate(10);
        viewHolder.rippleView.setRippleDuration(200);

        view.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                intent.putExtra(Constants.EXTRA_MOVIE_ID, mMovieList.get(position).getId());
                intent.putExtra(Constants.EXTRA_MOVIE, new MovieParcel(mMovieList.get(position)));
                //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view.findViewById(R.id.imageView), "poster");
                //ActivityCompat.startActivity(mActivity, intent, options.toBundle());
                mContext.startActivity(intent);*/
            }
        });

        viewHolder.rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {

            @Override
            public void onComplete(RippleView rippleView) {
                int position = viewHolder.getAdapterPosition();
                Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                intent.putExtra(Constants.EXTRA_MOVIE_ID, mMovieList.get(position).getId());
                intent.putExtra(Constants.EXTRA_MOVIE, new MovieParcel(mMovieList.get(position)));
                //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view.findViewById(R.id.imageView), "poster");
                //ActivityCompat.startActivity(mActivity, intent, options.toBundle());
                mContext.startActivity(intent);
            }

        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        Picasso.with(mContext)
                .load(PosterUtility.getFullPosterPath342(movie.getPosterPath()))
                .into(holder.imageView);
    }

    public void setMovieList(List<Movie> movieList) {
        this.mMovieList.clear();
        this.mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }

    public void clear() {
        this.mMovieList.clear();
        notifyDataSetChanged();
    }
}
