package com.github.thiagolocatelli.popularmovies.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.thiagolocatelli.moviedb.model.Credit;
import com.github.thiagolocatelli.popularmovies.app.R;
import com.github.thiagolocatelli.popularmovies.app.util.PosterUtility;

import java.util.List;

/**
 * Created by thiago on 5/6/16.
 */
public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder>  {

    private List<Credit> cast;
    private Context mContext;

    public CastAdapter(Context context, List<Credit> cast) {
        this.cast = cast;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cast, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.lblRealName.setText(cast.get(position).getName());
        viewHolder.lblInMovieName.setText(cast.get(position).getCharacter());
        //viewHolder.imgPoster.setTag(cast.get(position));

        Glide.with(mContext)
                .load(PosterUtility.getFullPosterPath342(cast.get(position).getProfilePath()))
                .placeholder(R.drawable.movie_placeholder)
                .fitCenter()
                .into(viewHolder.imgPoster);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgPoster;
        public TextView lblRealName;
        public TextView lblInMovieName;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            imgPoster = (ImageView) itemLayoutView.findViewById(R.id.imgPoster);
            lblRealName = (TextView) itemLayoutView.findViewById(R.id.lblRealName);
            lblInMovieName = (TextView) itemLayoutView.findViewById(R.id.lblInMovieName);
        }
    }

    @Override
    public int getItemCount() {
        return cast.size();
    }
}
