package com.github.thiagolocatelli.popularmovies.app.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by thiago on 5/6/16.
 */
@ContentProvider(authority = MovieProvider.AUTHORITY, database = MovieDatabase.class)
public class MovieProvider {

    public static final String AUTHORITY = "com.github.thiagolocatelli.popularmovies.app";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String MOVIES = "movies";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }


    @TableEndpoint(table = MovieDatabase.MOVIES)
    public static class Movies {

        @ContentUri(
                path = Path.MOVIES,
                type = "vnd.android.cursor.dir/list")
        public static final Uri CONTENT_URI = buildUri(Path.MOVIES);

        @InexactContentUri(
                path = Path.MOVIES + "/#",
                name = "MOVIE_ID",
                type = "vnd.android.cursor.item/list",
                whereColumn = MovieColumns._ID,
                pathSegment = 1)
        public static Uri withId(long id) {
            return buildUri(Path.MOVIES, String.valueOf(id));
        }

    }
}
