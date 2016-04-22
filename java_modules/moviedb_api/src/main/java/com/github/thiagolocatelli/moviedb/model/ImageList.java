package com.github.thiagolocatelli.moviedb.model;

import java.util.List;

/**
 * Created by thiago on 4/20/16.
 */
public class ImageList {

    private List<Image> backdrops;
    private List<Image> posters;

    public List<Image> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Image> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Image> getPosters() {
        return posters;
    }

    public void setPosters(List<Image> posters) {
        this.posters = posters;
    }
}
