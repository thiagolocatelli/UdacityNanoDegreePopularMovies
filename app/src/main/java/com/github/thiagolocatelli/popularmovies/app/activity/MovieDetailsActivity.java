package com.github.thiagolocatelli.popularmovies.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.thiagolocatelli.popularmovies.app.fragment.MovieDetailsFragment;
import com.github.thiagolocatelli.popularmovies.app.util.Constants;

import com.github.thiagolocatelli.popularmovies.app.R;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EXTRA_MOVIE_ID, getIntent().getLongExtra(Constants.EXTRA_MOVIE_ID, 0l));
        bundle.putParcelable(Constants.EXTRA_MOVIE, getIntent().getParcelableExtra(Constants.EXTRA_MOVIE));

        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        movieDetailsFragment.setArguments(bundle);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, movieDetailsFragment)
                    .commit();
        }

    }

}
