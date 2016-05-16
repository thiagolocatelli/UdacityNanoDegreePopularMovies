package com.github.thiagolocatelli.moviedb;

import com.github.thiagolocatelli.moviedb.model.Movie;
import com.github.thiagolocatelli.moviedb.model.MovieList;
import com.github.thiagolocatelli.moviedb.model.Reviews;
import com.github.thiagolocatelli.moviedb.model.MovieTrailersList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by thiago on 4/20/16.
 */
public interface MovieDBService {

    @GET("movie/{id}?append_to_response=trailers,credits,similar,images,releases,reviews")
    Call<Movie> getMovie(@Path("id") Long movieId);

    @GET("movie/{id}/videos")
    Call<MovieTrailersList> getMovieTrailers(@Path("id") Long movieId);

    @GET("movie/{id}/reviews")
    Call<Reviews> getMovieReviews(@Path("id") Long movieId);

    @GET("movie/now_playing?language=en")
    Call<MovieList> getNowPlayingMovies();

    @GET("movie/popular?language=en")
    Call<MovieList> getMostPopularMovies();

    @GET("movie/top_rated?language=en")
    Call<MovieList> getTopRatedMovies();

    @GET("movie/upcoming?language=en")
    Call<MovieList> getUpcomingMovies();

}
