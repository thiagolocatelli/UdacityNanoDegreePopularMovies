package com.github.thiagolocatelli.popularmovies.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.thiagolocatelli.moviedb.MovieDBApi;
import com.github.thiagolocatelli.moviedb.model.Credit;
import com.github.thiagolocatelli.moviedb.model.Movie;
import com.github.thiagolocatelli.moviedb.model.Review;
import com.github.thiagolocatelli.moviedb.model.Video;
import com.github.thiagolocatelli.popularmovies.app.PopularMoviesApplication;
import com.github.thiagolocatelli.popularmovies.app.R;
import com.github.thiagolocatelli.popularmovies.app.adapter.CastAdapter;
import com.github.thiagolocatelli.popularmovies.app.adapter.CrewAdapter;
import com.github.thiagolocatelli.popularmovies.app.adapter.TrailerAdapter;
import com.github.thiagolocatelli.popularmovies.app.async.LoadMovieAsync;
import com.github.thiagolocatelli.popularmovies.app.parcel.MovieParcel;
import com.github.thiagolocatelli.popularmovies.app.util.Constants;
import com.github.thiagolocatelli.popularmovies.app.util.DatabaseUtility;
import com.github.thiagolocatelli.popularmovies.app.util.PosterUtility;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsFragment extends Fragment implements LoadMovieAsync.LoadMovieListener,
        AppBarLayout.OnOffsetChangedListener {

    private static final String LOG_TAG = MovieDetailsFragment.class.getSimpleName();
    private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;

    private MovieDBApi mMovieDBApi;
    private LoadMovieAsync mLoadMovieAsync;
    //@BindView(R.id.movie_backdrop) ImageView mBackdropImage;
    @BindView(R.id.movie_poster) ImageView mMoviePoster;
    @BindView(R.id.movie_title) TextView mTitle;
    @BindView(R.id.movie_description) TextView mDescription;
    @BindView(R.id.movie_duration) TextView mMovieDuration;
    @BindView(R.id.movie_release_year) TextView mMovieReleaseYear;
    @BindView(R.id.movie_rating) TextView mMovieRating;
    @BindView(R.id.fab) FloatingActionButton mFab;
    @BindView(R.id.castRecyclerView) RecyclerView mCastRecyclerView;
    @BindView(R.id.crewRecyclerView) RecyclerView mCrewRecyclerView;
    @BindView(R.id.trailersRecyclerView) RecyclerView mTrailersRecyclerView;
    @BindView(R.id.scrollView) ScrollView mScrollView;
    @BindView(R.id.reviews_container) ViewGroup mReviewsContainer;

    private MovieParcel mMovie;
    private int mMaxScrollSize;
    private boolean mIsImageHidden;

    public MovieDetailsFragment() {
        //setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, mRootView);

        if(getArguments() != null) {
            mMovie = getArguments().getParcelable(Constants.EXTRA_MOVIE);
            mMovieReleaseYear.setText(getYear(mMovie.getReleaseDate()));
            mTitle.setText(mMovie.getTitle());
            mDescription.setText(mMovie.getSynopsis());

            mFab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(DatabaseUtility.isFavorited(getActivity(), mMovie.getMovieId())) {
                        DatabaseUtility.removeFavorite(getActivity(), mMovie.getMovieId());
                        mFab.setImageResource(R.drawable.ic_heart_outline_white_48dp);
                    }
                    else {
                        DatabaseUtility.addFavorite(getContext(), mMovie);
                        mFab.setImageResource(R.drawable.ic_heart_white_48dp);
                    }
                }
            });

            if(DatabaseUtility.isFavorited(getActivity(), mMovie.getMovieId())) {
                mFab.setImageResource(R.drawable.ic_heart_white_48dp);
            }
            else {
                mFab.setImageResource(R.drawable.ic_heart_outline_white_48dp);
            }

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            mTrailersRecyclerView.setLayoutManager(layoutManager);

            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            mCastRecyclerView.setLayoutManager(layoutManager);

            layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            mCrewRecyclerView.setLayoutManager(layoutManager);

            mCastRecyclerView.setAdapter(new CastAdapter(getContext(), new ArrayList<Credit>()));
            mCrewRecyclerView.setAdapter(new CrewAdapter(getContext(), new ArrayList<Credit>()));
            mTrailersRecyclerView.setAdapter(new TrailerAdapter(getContext(), new ArrayList<Video>()));

        }

        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PopularMoviesApplication application = (PopularMoviesApplication) getActivity().getApplication();
        this.mMovieDBApi = application.movieDBApi();
        this.mLoadMovieAsync = new LoadMovieAsync(mMovieDBApi, this);

        if(mMovie != null) {
            Picasso.with(getActivity())
                    .load(PosterUtility.getFullPosterPath342(mMovie.getPosterPath()))
                    .placeholder(R.drawable.movie_placeholder)
                    .into(mMoviePoster);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if(getArguments() != null) {
            mLoadMovieAsync.setMovieId(getArguments().getLong(Constants.EXTRA_MOVIE_ID));
            mLoadMovieAsync.execute();
        }
    }

    @Override
    public void onMovieLoaded(Movie movie) {
        Log.d(LOG_TAG, "Movie Details Loaded");

        mMovieDuration.setText("Duration: " + convertTime(movie.getRuntime()));
        mMovieRating.setText("Rating: " + movie.getVoteAverage().toString() + "/10");

        mMovie.setRuntime(movie.getRuntime());
        mMovie.setVoteAverage(movie.getVoteAverage());

        mCastRecyclerView.setAdapter(new CastAdapter(getContext(), movie.getCredits().getCast()));
        mCrewRecyclerView.setAdapter(new CrewAdapter(getContext(), movie.getCredits().getCrew()));
        mTrailersRecyclerView.setAdapter(new TrailerAdapter(getContext(), movie.getTrailers().getYoutube()));
                mScrollView.setVisibility(View.VISIBLE);

        populateReviews(movie.getReviews().getResults());

//        mScrollView.post(new Runnable() {
//            public void run() {
//                mScrollView.fullScroll(ScrollView.FOCUS_UP);
//                mScrollView.scrollTo(0, 0);
//            }
//        });
    }

    private void populateReviews(List<Review> reviews) {

        for (int i = mReviewsContainer.getChildCount() - 1; i >= 2; i--) {
            mReviewsContainer.removeViewAt(i);
        }

        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        if(reviews != null && reviews.size() > 0) {
            for(Review review : reviews) {
                final View reviewView = inflater.inflate(R.layout.item_review, mReviewsContainer, false);
                final TextView reviewAuthorView = ButterKnife.findById(reviewView, R.id.review_author);
                final TextView reviewContentView = ButterKnife.findById(reviewView, R.id.review_content);
                reviewAuthorView.setText(review.getAuthor());
                reviewContentView.setText(review.getContent());
                mReviewsContainer.addView(reviewView);
            }
        }
        else {
            mReviewsContainer.setVisibility(View.GONE);
        }

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

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int currentScrollPercentage = (Math.abs(verticalOffset)) * 100
                / mMaxScrollSize;

        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!mIsImageHidden) {
                mIsImageHidden = true;

                ViewCompat.animate(mFab).scaleY(0).scaleX(0).start();
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                ViewCompat.animate(mFab).scaleY(1).scaleX(1).start();
            }
        }
    }
}
