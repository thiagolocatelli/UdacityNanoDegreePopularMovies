package com.github.thiagolocatelli.moviedb;

import com.github.thiagolocatelli.moviedb.model.Movie;
import com.github.thiagolocatelli.moviedb.model.MovieList;
import com.github.thiagolocatelli.moviedb.model.Reviews;
import com.github.thiagolocatelli.moviedb.model.MovieTrailersList;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thiago on 4/20/16.
 */
public class MovieDBApi {

    private static final String API_ENDPOINT = "https://api.themoviedb.org/3/";

    private MovieDBService movieDbService;

    public MovieDBApi(final String apiKey) {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(new Interceptor() {

                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", apiKey).build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                }

            }).build();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        movieDbService = retrofit.create(MovieDBService.class);
    }

    public Call<Movie> getMovie(Long movieId) {
        return movieDbService.getMovie(movieId);
    }

    public Call<MovieList> getNowPlayingMovies() {
        return movieDbService.getNowPlayingMovies();
    }

    public Call<MovieList> getMostPopularMovies() {
        return movieDbService.getMostPopularMovies();
    }

    public Call<MovieList> getTopRatedMovies() {
        return movieDbService.getTopRatedMovies();
    }

    public Call<MovieList> getUpcomingMovies() {
        return movieDbService.getUpcomingMovies();
    }

    public Call<Reviews> getMovieReviews(Long movieId) {
        return movieDbService.getMovieReviews(movieId);
    }

    public Call<MovieTrailersList> getMovieTrailers(Long movieId) {
        return movieDbService.getMovieTrailers(movieId);
    }

}
