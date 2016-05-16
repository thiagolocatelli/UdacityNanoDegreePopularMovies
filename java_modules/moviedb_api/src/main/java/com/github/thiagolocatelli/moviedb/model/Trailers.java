package com.github.thiagolocatelli.moviedb.model;

import java.util.List;

/**
 * Created by thiago on 5/6/16.
 */
public class Trailers {

    private List<Video> quicktime;
    private List<Video> youtube;

    public List<Video> getQuicktime() {
        return quicktime;
    }

    public void setQuicktime(List<Video> quicktime) {
        this.quicktime = quicktime;
    }

    public List<Video> getYoutube() {
        return youtube;
    }

    public void setYoutube(List<Video> youtube) {
        this.youtube = youtube;
    }
}
