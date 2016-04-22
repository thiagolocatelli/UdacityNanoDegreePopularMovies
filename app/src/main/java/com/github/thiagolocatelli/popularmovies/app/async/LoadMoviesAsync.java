package com.github.thiagolocatelli.popularmovies.app.async;

import com.github.thiagolocatelli.moviedb.MovieDBApi;
import com.github.thiagolocatelli.moviedb.model.MovieList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by thiago on 4/20/16.
 */
public class LoadMoviesAsync extends SafeAsyncTask<MovieList> {

    public interface LoadMoviesListener {
        void onMovieListLoaded(MovieList movieList);
        void onMovieListLoadError(Exception e);
    }

    private static int LIST_NOW_PLAYING = 0;
    private static int LIST_MOST_POPULAR = 1;
    private static int LIST_TOP_RATED = 2;
    private static int LIST_TOP_UPCOMING = 3;

    private MovieDBApi mMovieDBApi;
    private int mListType = LIST_MOST_POPULAR;
    private LoadMoviesListener mLoadMoviesListener;

    public LoadMoviesAsync(MovieDBApi movieDBApi, LoadMoviesListener loadMoviesListener) {
        this.mMovieDBApi = movieDBApi;
        this.mLoadMoviesListener = loadMoviesListener;
    }

    public void setListType(int listType) {
        this.mListType = listType;
    }

    @Override
    public MovieList call() throws Exception {
        Call<MovieList> movieListCall;

        if(mListType == LIST_NOW_PLAYING) {
            movieListCall = mMovieDBApi.getNowPlayingMovies();
        }
        else if(mListType == LIST_MOST_POPULAR) {
            movieListCall = mMovieDBApi.getMostPopularMovies();
        }
        else if(mListType == LIST_TOP_RATED) {
            movieListCall = mMovieDBApi.getTopRatedMovies();
        }
        else {
            movieListCall = mMovieDBApi.getUpcomingMovies();
        }

        Response<MovieList> movieListResponse = movieListCall.execute();
        return movieListResponse.body();
    }

    @Override
    protected void onSuccess(MovieList movieList) throws Exception {
        if(mLoadMoviesListener != null) {
            mLoadMoviesListener.onMovieListLoaded(movieList);
        }
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        if(mLoadMoviesListener != null) {
            mLoadMoviesListener.onMovieListLoadError(e);
        }
    }
}
