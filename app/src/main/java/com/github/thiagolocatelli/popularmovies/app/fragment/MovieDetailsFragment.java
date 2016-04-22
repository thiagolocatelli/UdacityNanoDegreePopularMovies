package com.github.thiagolocatelli.popularmovies.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.thiagolocatelli.moviedb.MovieDBApi;
import com.github.thiagolocatelli.moviedb.model.Movie;
import com.github.thiagolocatelli.popularmovies.app.PopularMoviesApplication;
import com.github.thiagolocatelli.popularmovies.app.parcel.MovieParcel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.github.thiagolocatelli.popularmovies.app.R;
import com.github.thiagolocatelli.popularmovies.app.async.LoadMovieAsync;
import com.github.thiagolocatelli.popularmovies.app.util.Constants;
import com.github.thiagolocatelli.popularmovies.app.util.PosterUtility;

public class MovieDetailsFragment extends Fragment implements LoadMovieAsync.LoadMovieListener {

    private static final String LOG_TAG = MovieDetailsFragment.class.getSimpleName();

    private MovieDBApi mMovieDBApi;
    private LoadMovieAsync mLoadMovieAsync;

    private ImageView mBackdropImage;
    private ImageView mMoviePoster;
    private TextView title;
    private TextView description;
    private TextView mMovieDuration;
    private TextView mMovieReleaseYear;
    private TextView mMovieRating;

    private MovieParcel mMovie;

    public MovieDetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        title = (TextView) mRootView.findViewById(R.id.movie_title);
        description = (TextView) mRootView.findViewById(R.id.movie_description);
        mMoviePoster = (ImageView) mRootView.findViewById(R.id.movie_poster);
        mMovieDuration = (TextView) mRootView.findViewById(R.id.movie_duration);
        mMovieReleaseYear = (TextView) mRootView.findViewById(R.id.movie_release_year);
        mMovieRating = (TextView) mRootView.findViewById(R.id.movie_rating);

        mMovie = (MovieParcel) getArguments().getParcelable(Constants.EXTRA_MOVIE);

        title.setText(mMovie.getTitle());
        description.setText(mMovie.getSynopsis());
        //mMovieDuration.setText(convertTime(mMovie.getRuntime()));
        mMovieReleaseYear.setText(getYear(mMovie.getReleaseDate()));

        Picasso.with(getActivity())
                .load(PosterUtility.getFullPosterPath342(mMovie.getPosterPath()))
                .into(mMoviePoster);

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PopularMoviesApplication application = (PopularMoviesApplication) getActivity().getApplication();
        this.mMovieDBApi = application.movieDBApi();
        this.mLoadMovieAsync = new LoadMovieAsync(mMovieDBApi, this);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        //mBackdropImage = (ImageView) getActivity().findViewById(R.id.backdrop);
    }

    @Override
    public void onStart() {
        super.onStart();
        mLoadMovieAsync.setMovieId(getArguments().getLong(Constants.EXTRA_MOVIE_ID));
        mLoadMovieAsync.execute();
    }

    @Override
    public void onMovieLoaded(Movie movie) {
        Log.d(LOG_TAG, "Movie Details Loaded");

        //Picasso.with(getActivity())
        //        .load(PosterUtility.getFullPosterPath342(movie.getBackdropPath()))
        //        .into(mBackdropImage);



        //title.setText(movie.getOriginalTitle());
        //description.setText(movie.getOverview());
        mMovieDuration.setText("Duration: " + convertTime(movie.getRuntime()));
        mMovieRating.setText("Rating: " + movie.getVoteAverage().toString() + "/10");

        //mMovieReleaseYear.setText(getYear(movie.getReleaseDate()));

        //CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        //toolbarLayout.setTitle(movie.getTitle());
    }

    @Override
    public void onMovieLoadError(Exception e) {
        Log.e(LOG_TAG, "Error while loading movie details: " + e.getMessage());
    }

    private String convertTime(long t) {
        if (t == 0) {
            return "-";
        }
        long hours = t / 60; //since both are ints, you get an int
        long minutes = t % 60;
        return (hours + " hr. " + minutes + " min.");
    }

    private String getYear(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d = format.parse(date);
            SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy");
            return serverFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return " - ";
    }
}
