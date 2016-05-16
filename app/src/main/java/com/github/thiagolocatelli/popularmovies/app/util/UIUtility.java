package com.github.thiagolocatelli.popularmovies.app.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by thiago on 5/12/16.
 */
public class UIUtility {

    public static boolean isTablet(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        float scaleFactor = metrics.density;
        float widthDp = widthPixels / scaleFactor;
        float heightDp = heightPixels / scaleFactor;
        float smallestWidth = Math.min(widthDp, heightDp);
        return  smallestWidth >= 600;
    }
}
