package com.github.thiagolocatelli.moviedb.model;

import java.util.List;

/**
 * Created by thiago on 4/20/16.
 */
public class MovieList {

    private Integer page;
    private Integer totalResults;
    private Integer totalPages;
    private List<Movie> results;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
