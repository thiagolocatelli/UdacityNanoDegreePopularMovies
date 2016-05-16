package com.github.thiagolocatelli.popularmovies.app.database;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

/**
 * Created by thiago on 5/6/16.
 */
public class MovieColumns {

    @DataType(DataType.Type.INTEGER) @PrimaryKey
    public static final String _ID = "_id";

    @DataType(DataType.Type.TEXT) @NotNull
    public static final String TITLE = "title";

    @DataType(DataType.Type.TEXT) @NotNull
    public static final String DESCRIPTION = "description";

    @DataType(DataType.Type.TEXT) @NotNull
    public static final String RELEASE_DATE = "release_date";

    @DataType(DataType.Type.INTEGER) @NotNull
    public static final String DURATION = "duration";

    @DataType(DataType.Type.REAL) @NotNull
    public static final String RATING = "rating";

    @DataType(DataType.Type.TEXT) @NotNull
    public static final String POSTER_ART = "poster_art";

    @DataType(DataType.Type.TEXT) @NotNull
    public static final String BACKDROP = "backdrop";

}
