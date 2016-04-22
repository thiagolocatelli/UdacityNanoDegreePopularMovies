package com.github.thiagolocatelli.popularmovies.app.util;

/**
 * Created by thiago on 4/20/16.
 */
public class PosterUtility {

    private static final String BASE_PATH = "http://image.tmdb.org/t/p/";

    public static String getFullPosterPath780(String posterPath) {
        return BASE_PATH + "w780" + posterPath;
    }

    public static String getFullPosterPath500(String posterPath) {
        return BASE_PATH + "w500" + posterPath;
    }

    public static String getFullPosterPath342(String posterPath) {
        return BASE_PATH + "w342" + posterPath;
    }

    public static String getFullPosterPath185(String posterPath) {
        return BASE_PATH + "w185" + posterPath;
    }

    public static String getFullPosterPath154(String posterPath) {
        return BASE_PATH + "w154" + posterPath;
    }
}
