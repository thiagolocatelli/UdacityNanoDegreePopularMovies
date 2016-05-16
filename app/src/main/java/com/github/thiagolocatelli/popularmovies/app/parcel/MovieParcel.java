package com.github.thiagolocatelli.popularmovies.app.parcel;

import android.os.Parcel;
import android.os.Parcelable;

import com.github.thiagolocatelli.moviedb.model.Movie;

/**
 * Created by thiago on 4/21/16.
 */
public class MovieParcel implements Parcelable {

    private Long movieId;
    private String title;
    private String synopsis;
    private String releaseDate;
    private String posterPath;
    private String backdrop;

    private Long runtime;
    private Double voteAverage;

    public MovieParcel() {}

    public MovieParcel(Movie movie) {
        movieId = movie.getId();
        title = movie.getTitle();
        synopsis = movie.getOverview();
        releaseDate = movie.getReleaseDate();
        posterPath = movie.getPosterPath();
        backdrop = movie.getBackdropPath();
    }

    public MovieParcel(Parcel source) {
        movieId = source.readLong();
        title = source.readString();
        synopsis = source.readString();
        releaseDate = source.readString();
        posterPath = source.readString();
        backdrop = source.readString();
        runtime = source.readLong();
        voteAverage = source.readDouble();

        if(runtime.equals(-1l)) {
            runtime = null;
        }

        if(voteAverage.equals(-1.0)) {
            voteAverage = null;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(movieId);
        parcel.writeString(title);
        parcel.writeString(synopsis);
        parcel.writeString(releaseDate);
        parcel.writeString(posterPath);
        parcel.writeString(backdrop);
        parcel.writeLong(runtime == null ? -1l : runtime);
        parcel.writeDouble(voteAverage == null ? -1.0 : voteAverage);
    }

    public static final Parcelable.Creator<MovieParcel> CREATOR = new Parcelable.Creator<MovieParcel>() {
        public MovieParcel createFromParcel(Parcel in) {
            return new MovieParcel(in);
        }

        public MovieParcel[] newArray(int size) {
            return new MovieParcel[size];
        }
    };

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public Long getRuntime() {
        return runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
}
