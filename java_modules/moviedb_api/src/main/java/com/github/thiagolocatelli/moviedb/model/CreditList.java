package com.github.thiagolocatelli.moviedb.model;

import java.util.List;

/**
 * Created by thiago on 4/20/16.
 */
public class CreditList {

    private Long id;
    private List<Credit> cast;
    private List<Credit> crew;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Credit> getCast() {
        return cast;
    }

    public void setCast(List<Credit> cast) {
        this.cast = cast;
    }

    public List<Credit> getCrew() {
        return crew;
    }

    public void setCrew(List<Credit> crew) {
        this.crew = crew;
    }
}
