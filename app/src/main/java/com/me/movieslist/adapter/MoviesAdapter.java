package com.me.movieslist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.movieslist.R;
import com.me.movieslist.config.UrlConfig;
import com.me.movieslist.network.parser.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xvbp3947 on 10/11/18.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {


    private List<Movie> mMoviesList;
    private Context mContext;
    private static MoviesViewHolderClicks mListener;

    public interface MoviesViewHolderClicks {
        public void onItemClick(View v, int position);
    }

    public static class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.movie_poster_iv)
        ImageView mMoviePosterIv;

        @BindView(R.id.movie_name_tv)
        TextView mMovieNameTv;

        @BindView(R.id.movie_rate_tv)
        TextView mMovieRateTv;


        public MoviesViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v, this.getLayoutPosition());
        }

    }

    public MoviesAdapter(Context nContext, List nMoviesList) {
        mContext = nContext;
        mMoviesList = nMoviesList;
    }

    public void setMoviesList(List<Movie> mMoviesList) {
        this.mMoviesList = mMoviesList;
    }

    public static void setListener(MoviesViewHolderClicks mListener) {
        MoviesAdapter.mListener = mListener;
    }

    @Override
    public MoviesAdapter.MoviesViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_list_item, parent, false);
        MoviesViewHolder vh = new MoviesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MoviesViewHolder holder, int position) {

        Movie mMovie = mMoviesList.get(position);
        holder.mMovieNameTv.setText(mMovie.getTitle());
        holder.mMovieRateTv.setText(String.valueOf(mMovie.getVoteAverage()));
        Picasso.with(mContext)
                .load(UrlConfig.getOriginalTMDBImagePathW500(mMovie.getBackDropPath()))
                .into(holder.mMoviePosterIv);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }
}