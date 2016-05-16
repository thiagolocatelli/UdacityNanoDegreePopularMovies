package com.github.thiagolocatelli.popularmovies.app.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.github.thiagolocatelli.popularmovies.app.database.MovieColumns;
import com.github.thiagolocatelli.popularmovies.app.database.MovieProvider;
import com.github.thiagolocatelli.popularmovies.app.parcel.MovieParcel;

/**
 * Created by thiago on 5/6/16.
 */
public class DatabaseUtility {


    public static boolean isFavorited(Context context, Long movieId) {
        Cursor cursor = context.getContentResolver().query(
                MovieProvider.Movies.CONTENT_URI,
                null,   // projection
                MovieColumns._ID + " = ?", // selection
                new String[] { Long.toString(movieId) },   // selectionArgs
                null    // sort order
        );
        int numRows = cursor.getCount();
        cursor.close();
        return numRows != 0;
    }

    public static void addFavorite(Context context, MovieParcel movieParcel) {
        if(!isFavorited(context, movieParcel.getMovieId())) {
            ContentValues cv = new ContentValues();
            cv.put(MovieColumns._ID, movieParcel.getMovieId());
            cv.put(MovieColumns.TITLE, movieParcel.getTitle());
            cv.put(MovieColumns.DESCRIPTION, movieParcel.getSynopsis());
            cv.put(MovieColumns.BACKDROP, movieParcel.getBackdrop());
            cv.put(MovieColumns.POSTER_ART, movieParcel.getPosterPath());
            cv.put(MovieColumns.DURATION, movieParcel.getRuntime());
            cv.put(MovieColumns.RELEASE_DATE, movieParcel.getReleaseDate());
            cv.put(MovieColumns.RATING, movieParcel.getVoteAverage());
            Uri uri = context.getContentResolver().insert(MovieProvider.Movies.CONTENT_URI, cv);
            Log.d("DatabaseUtitlity", "uri: " + uri);
        }
    }

    public static void removeFavorite(Context context, Long movieId) {
        if(isFavorited(context, movieId)) {
            int result = context.getContentResolver().delete(MovieProvider.Movies.withId(movieId), null, null);
            Log.d("DatabaseUtitlity", "result: " + result);
        }
    }


}
