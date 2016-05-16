package com.github.thiagolocatelli.popularmovies.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.github.thiagolocatelli.popularmovies.app.R;
import com.github.thiagolocatelli.popularmovies.app.fragment.MovieDetailsFragment;
import com.github.thiagolocatelli.popularmovies.app.fragment.MoviesFragment;
import com.github.thiagolocatelli.popularmovies.app.parcel.MovieParcel;
import com.github.thiagolocatelli.popularmovies.app.util.Constants;


public class MainActivity extends ActionBarActivity implements MoviesFragment.OnMovieSelectedListener {

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;
    private MoviesFragment moviesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_container, new MovieDetailsFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        }
        else {
            mTwoPane = false;
        }


        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.TWO_PANES, mTwoPane);
        moviesFragment = new MoviesFragment();
        moviesFragment.setArguments(bundle);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, moviesFragment).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onMovieListSelected(MovieParcel movie) {
        if(mTwoPane) {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.EXTRA_MOVIE_ID, movie.getMovieId());
            bundle.putParcelable(Constants.EXTRA_MOVIE, movie);

            MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
            movieDetailsFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, movieDetailsFragment, DETAILFRAGMENT_TAG)
                    .commit();
        }
        else {
            Intent intent = new Intent(this, MovieDetailsActivity.class);
            intent.putExtra(Constants.EXTRA_MOVIE_ID, movie.getMovieId());
            intent.putExtra(Constants.EXTRA_MOVIE, movie);
            //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity, view.findViewById(R.id.imageView), "poster");
            //ActivityCompat.startActivity(mActivity, intent, options.toBundle());
            startActivity(intent);
        }
    }
}
