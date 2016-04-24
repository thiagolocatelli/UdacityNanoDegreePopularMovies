package com.github.thiagolocatelli.popularmovies.app.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.thiagolocatelli.moviedb.MovieDBApi;
import com.github.thiagolocatelli.moviedb.model.Movie;
import com.github.thiagolocatelli.moviedb.model.MovieList;
import com.github.thiagolocatelli.popularmovies.app.PopularMoviesApplication;
import com.github.thiagolocatelli.popularmovies.app.async.LoadMoviesAsync;

import com.github.thiagolocatelli.popularmovies.app.R;
import com.github.thiagolocatelli.popularmovies.app.adapter.MoviesAdapter;
import com.github.thiagolocatelli.popularmovies.app.parcel.MovieParcel;
import com.github.thiagolocatelli.popularmovies.app.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesFragment extends Fragment implements LoadMoviesAsync.LoadMoviesListener {

    private static final String LOG_TAG = MoviesFragment.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;
    private LoadMoviesAsync mLoadMoviesAsync;
    private MovieDBApi mMovieDBApi;

    private int mCurrentSortType;

    private ArrayList<MovieParcel> myMovieList;

    public MoviesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mRootView = inflater.inflate(R.layout.fragment_movies, container, false);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mAdapter = new MoviesAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null) {
            myMovieList = savedInstanceState.getParcelableArrayList(Constants.MOVIE_LIST);
            if(myMovieList != null) {
                mAdapter.setMovieList(fromMovieParcelList(myMovieList));
                mAdapter.notifyDataSetChanged();
            }
        }

        PopularMoviesApplication application = (PopularMoviesApplication) getActivity().getApplication();
        this.mMovieDBApi = application.movieDBApi();
        this.mLoadMoviesAsync = new LoadMoviesAsync(mMovieDBApi, this);

        mCurrentSortType = getSortTypeFromPreferences();
        mLoadMoviesAsync.setListType(mCurrentSortType);
        if(myMovieList == null) {
            mAdapter.clear();
            mLoadMoviesAsync.execute();
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Movies - " + getSortTypeTitle());

    }

    private List<Movie> fromMovieParcelList(List<MovieParcel> list) {
        List<Movie> movies = new ArrayList<>();
        for(MovieParcel movieParcel : myMovieList) {
            Movie movie = new Movie();
            movie.setId(movieParcel.getMovieId());
            movie.setPosterPath(movieParcel.getPosterPath());
            movie.setBackdropPath(movieParcel.getBackdrop());
            movie.setTitle(movieParcel.getTitle());
            movie.setOverview(movieParcel.getSynopsis());
            movie.setReleaseDate(movieParcel.getReleaseDate());
            movies.add(movie);
        }
        return movies;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(Constants.MOVIE_LIST, myMovieList);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "started");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "resumed");
        if(mCurrentSortType != getSortTypeFromPreferences()) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Movies - " + getSortTypeTitle());
            mCurrentSortType = getSortTypeFromPreferences();
            mAdapter.clear();
            mLoadMoviesAsync.setListType(mCurrentSortType);
            mLoadMoviesAsync.execute();
        }
    }

    @Override
    public void onMovieListLoaded(MovieList movieList) {

        ArrayList<MovieParcel> localMovieList = new ArrayList<>();
        for(Movie movie : movieList.getResults()) {
            localMovieList.add(new MovieParcel(movie));
        }

        myMovieList = localMovieList;

        mAdapter.setMovieList(movieList.getResults());
    }

    @Override
    public void onMovieListLoadError(Exception e) {
        Log.e(LOG_TAG, e.getMessage());
    }

    private int getSortTypeFromPreferences() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String value = prefs.getString(getString(R.string.pref_sorttype_key), getString(R.string.pref_sorttype_default));
        return Integer.valueOf(value);
    }

    private String getSortTypeTitle() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String value = prefs.getString(getString(R.string.pref_sorttype_key), getString(R.string.pref_sorttype_default));
        return getResources().getStringArray(R.array.sort_types)[Integer.valueOf(value)];
    }
}
