package com.github.thiagolocatelli.popularmovies.app.async;

import com.github.thiagolocatelli.moviedb.MovieDBApi;
import com.github.thiagolocatelli.moviedb.model.Movie;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by thiago on 4/20/16.
 */
public class LoadMovieReviewsAsync extends SafeAsyncTask<Movie> {

    public interface LoadMovieListener {
        void onMovieLoaded(Movie movie);
        void onMovieLoadError(Exception e);
    }

    private MovieDBApi mMovieDBApi;
    private LoadMovieListener mLoadMovieListener;
    private Long mMovieId;

    public LoadMovieReviewsAsync(MovieDBApi movieDBApi, LoadMovieListener loadMovieListener) {
        this.mMovieDBApi = movieDBApi;
        this.mLoadMovieListener = loadMovieListener;
    }

    @Override
    public Movie call() throws Exception {
        Call<Movie> getMovieCall = mMovieDBApi.getMovie(mMovieId);
        Response<Movie> getMovieResponse = getMovieCall.execute();
        return getMovieResponse.body();
    }

    @Override
    protected void onSuccess(Movie movie) throws Exception {
        if(mLoadMovieListener != null) {
            mLoadMovieListener.onMovieLoaded(movie);
        }
    }

    @Override
    protected void onException(Exception e) throws RuntimeException {
        if(mLoadMovieListener != null) {
            mLoadMovieListener.onMovieLoadError(e);
        }
    }

    public void setMovieId(Long movieId) {
        this.mMovieId = movieId;
    }
}
