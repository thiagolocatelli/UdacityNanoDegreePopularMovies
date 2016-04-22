package com.github.thiagolocatelli.moviedb;

import com.github.thiagolocatelli.moviedb.model.Movie;
import com.github.thiagolocatelli.moviedb.model.MovieList;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by thiago on 4/20/16.
 */
public class MovieDBApiTest {

    private static final String API_KEY = "ca8f06831f3ae106db50b2c86d56881d";

    @Test
    public void testGetMovie() throws IOException {
        MovieDBApi movieDbApi = new MovieDBApi(API_KEY);
        Call<Movie> moevieCall = movieDbApi.getMovie(550l);
        Response<Movie> movieResponse = moevieCall.execute();

        MatcherAssert.assertThat(movieResponse, Matchers.notNullValue());
        MatcherAssert.assertThat(movieResponse.body(), Matchers.notNullValue());
    }

    @Test
    public void testGetMostPopularMovies() throws IOException {
        MovieDBApi movieDbApi = new MovieDBApi(API_KEY);
        Call<MovieList> mostPopularMoviesCall = movieDbApi.getMostPopularMovies();
        Response<MovieList> mostPopularMoviesResponse = mostPopularMoviesCall.execute();

        MatcherAssert.assertThat(mostPopularMoviesResponse, Matchers.notNullValue());
        MatcherAssert.assertThat(mostPopularMoviesResponse.body(), Matchers.notNullValue());
    }

    @Test
    public void testGetTopRatedMovies() throws IOException {
        MovieDBApi movieDbApi = new MovieDBApi(API_KEY);
        Call<MovieList> topRatedMoviesCall = movieDbApi.getTopRatedMovies();
        Response<MovieList> topRatedMoviesResponse = topRatedMoviesCall.execute();

        MatcherAssert.assertThat(topRatedMoviesResponse, Matchers.notNullValue());
        MatcherAssert.assertThat(topRatedMoviesResponse.body(), Matchers.notNullValue());

    }

}
