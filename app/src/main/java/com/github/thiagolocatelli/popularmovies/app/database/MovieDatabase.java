package com.github.thiagolocatelli.popularmovies.app.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by thiago on 5/6/16.
 */
@Database(version = MovieDatabase.VERSION)
public class MovieDatabase {

    public static final int VERSION = 1;

    @Table(MovieColumns.class) public static final String MOVIES = "movies";

}
