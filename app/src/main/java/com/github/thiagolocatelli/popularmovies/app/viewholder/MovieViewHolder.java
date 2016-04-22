package com.github.thiagolocatelli.popularmovies.app.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.andexert.library.RippleView;
import com.github.thiagolocatelli.popularmovies.app.R;


/**
 * Created by thiago on 4/20/16.
 */
public class MovieViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public RippleView rippleView;

    public MovieViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        rippleView = (RippleView) itemView.findViewById(R.id.more);
    }
}