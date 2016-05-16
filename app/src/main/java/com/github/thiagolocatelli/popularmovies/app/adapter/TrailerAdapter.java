package com.github.thiagolocatelli.popularmovies.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.thiagolocatelli.moviedb.model.Video;
import com.github.thiagolocatelli.popularmovies.app.R;
import com.github.thiagolocatelli.popularmovies.app.util.PosterUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by thiago on 5/11/16.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>  {

    private List<Video> videos;
    private Context mContext;

    public TrailerAdapter(Context context, List<Video> cast) {
        this.videos = cast;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        viewHolder.imgPoster.setTag(videos.get(position));

        Picasso.with(mContext)
                .load(PosterUtility.getYouTubePosterPath(videos.get(position).getSource()))
                .placeholder(R.drawable.movie_placeholder)
                .into(viewHolder.imgPoster);

        viewHolder.imgPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videos.get(position).getSource())));
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgPoster;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            imgPoster = (ImageView) itemLayoutView.findViewById(R.id.trailerImage);
        }


    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}