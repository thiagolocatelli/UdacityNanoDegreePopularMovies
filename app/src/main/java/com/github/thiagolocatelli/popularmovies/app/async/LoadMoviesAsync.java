package com.github.thiagolocatelli.popularmovies.app.async;

import android.content.Context;
import android.database.Cursor;

import com.github.thiagolocatelli.moviedb.MovieDBApi;
import com.github.thiagolocatelli.moviedb.model.Movie;
import com.github.thiagolocatelli.moviedb.model.MovieList;
import com.github.thiagolocatelli.popularmovies.app.database.MovieColumns;
import com.github.thiagolocatelli.popularmovies.app.database.MovieProvider;
import com.github.thiagolocatelli.popularmovies.app.parcel.MovieParcel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by thiago on 4/20/16.
 */
public class LoadMoviesAsync extends SafeAsyncTask<List<MovieParcel>> {

    public interface LoadMoviesListener {
        void onMovieListLoadStart();
        void onMovieListLoaded(List<MovieParcel> movieList);
        void onMovieListLoadError(Exception e);
    }

    private static int LIST_NOW_PLAYING = 0;
    private static int LIST_MOST_POPULAR = 1;
    private static int LIST_TOP_RATED = 2;
    private static int LIST_TOP_UPCOMING = 3;
    private static int LIST_FAVORITES = 4;

    private Context mContext;
    private MovieDBApi mMovieDBApi;
    private int mListType = LIST_MOST_POPULAR;
    private LoadMoviesListener mLoadMoviesListener;

    public LoadMoviesAsync(Context context, MovieDBApi movieDBApi, LoadMoviesListener loadMoviesListener) {
        this.mContext = context;
        this.mMovieDBApi = movieDBApi;
        this.mLoadMoviesListener = loadMoviesListener;
    }

    public void setListType(int listType) {
        this.mListType = listType;
    }

    @Override
    protected void onPreExecute() throws Exception {
        if(mLoadMoviesListener != null) {
            mLoadMoviesListener.onMovieListLoadStart();
        }
    }

    @Override
    public List<MovieParcel> call() throws Exception {
        Response<MovieList> movieListResponse = null;
        List<MovieParcel> localMovieList = null;

        if(mListType == LIST_NOW_PLAYING) {
            movieListResponse = mMovieDBApi.getNowPlayingMovies().execute();
        }
        else if(mListType == LIST_MOST_POPULAR) {
            movieListResponse = mMovieDBApi.getMostPopularMovies().execute();
        }
        else if(mListType == LIST_TOP_RATED) {
            movieListResponse = mMovieDBApi.getTopRatedMovies().execute();
        }
        else if(mListType == LIST_TOP_UPCOMING) {
            movieListResponse = mMovieDBApi.getUpcomingMovies().execute();
        }
        else {
            localMovieList = queryFavoriteMovies();
        }

        if(movieListResponse != null) {
            localMovieList = new ArrayList<>();
            for (Movie movie : movieListResponse.body().getResults()) {
                localMovieList.add(new MovieParcel(movie));
            }
        }

        return localMovieList;
    }

    @Override
    protected void onSuccess(List<MovieParcel> movieList) throws Exception {
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

    private List<MovieParcel> queryFavoriteMovies() {
        List<MovieParcel> localMovieList = null;

        Cursor cursor = mContext.getContentResolver().query(MovieProvider.Movies.CONTENT_URI, null, null, null, null);
        try {
            if (cursor.moveToFirst()) {
                localMovieList = new ArrayList<>();
                do {
                    MovieParcel movie = new MovieParcel();
                    movie.setMovieId(cursor.getLong(cursor.getColumnIndex(MovieColumns._ID)));
                    movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieColumns.TITLE)));
                    movie.setSynopsis(cursor.getString(cursor.getColumnIndex(MovieColumns.DESCRIPTION)));
                    movie.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieColumns.POSTER_ART)));
                    movie.setBackdrop(cursor.getString(cursor.getColumnIndex(MovieColumns.BACKDROP)));
                    movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(MovieColumns.RATING)));
                    movie.setRuntime(cursor.getLong(cursor.getColumnIndex(MovieColumns.DURATION)));
                    movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieColumns.RELEASE_DATE)));
                    localMovieList.add(movie);
                }  while (cursor.moveToNext());
            }
        }
        finally {
            cursor.close();
        }

        return localMovieList;
    }
}
