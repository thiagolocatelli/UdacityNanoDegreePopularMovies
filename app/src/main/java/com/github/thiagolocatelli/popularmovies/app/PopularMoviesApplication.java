package com.github.thiagolocatelli.popularmovies.app;

import android.app.Application;

import com.github.thiagolocatelli.moviedb.MovieDBApi;

/**
 * Created by thiago on 4/20/16.
 */
public class PopularMoviesApplication extends Application {

    private MovieDBApi mMovieDBApi;

    @Override
    public void onCreate() {
        super.onCreate();
        mMovieDBApi = new MovieDBApi(BuildConfig.MOVIE_DB_API_KEY);
    }

    public MovieDBApi movieDBApi() {
        return mMovieDBApi;
    }
}
