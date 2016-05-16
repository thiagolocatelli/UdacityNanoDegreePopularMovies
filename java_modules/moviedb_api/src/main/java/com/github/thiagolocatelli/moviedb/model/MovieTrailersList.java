package com.github.thiagolocatelli.moviedb.model;

import java.util.List;

/**
 * Created by thiago on 5/6/16.
 */
public class MovieTrailersList {

    private Integer id;
    private List<Trailer> results;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Trailer> getResults() {
        return results;
    }

    public void setResults(List<Trailer> results) {
        this.results = results;
    }
}
