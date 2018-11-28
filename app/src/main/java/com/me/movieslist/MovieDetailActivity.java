package com.me.movieslist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.me.movieslist.config.UrlConfig;
import com.me.movieslist.network.MoviesClient;
import com.me.movieslist.network.ServiceGenerator;
import com.me.movieslist.network.parser.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailActivity extends AppCompatActivity {


    public static final String MOVIE_ID_ARG = "movie.id.arg";


    @BindView(R.id.movie_poster_iv)
    ImageView mMoviePosterIv;

    @BindView(R.id.movie_title_tv)
    TextView mMovieTitle;

    @BindView(R.id.movie_overview_tv)
    TextView mMovieOverviewTv;

    @BindView(R.id.movie_avg_views_tv)
    TextView mMovieAvgViewsTv;


    @BindView(R.id.detailed_status_tv)
    TextView mDetailedStatusTv;

    @BindView(R.id.detailed_status_pb)
    ProgressBar mDetailedStatusPb;

    String movieId = "-1";
    private Movie fetchedMovie;

    public static void launchActivity(Context from, Bundle extras) {
        Intent lIntent = new Intent(from, MovieDetailActivity.class);
        lIntent.putExtras(extras);
        from.startActivity(lIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        movieId = getIntent().getExtras().getString(MOVIE_ID_ARG);
        fetchTheMovie();
    }

    private void fetchTheMovie() {
        MoviesClient moviesClient = ServiceGenerator.createService(MoviesClient.class);
        Observable<Movie> movie = moviesClient.getMovie(movieId);
        movie.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<Movie>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        showLoadingMsg();
                    }

                    @Override
                    public void onNext(Movie fetchedMovie) {
                        MovieDetailActivity.this.fetchedMovie = fetchedMovie;
                    }

                    @Override
                    public void onError(Throwable e) {
                        showErrMsg();
                    }

                    @Override
                    public void onComplete() {
                        hideLoadingMsg();
                        onFetchMovieSuccess();

                    }
                });
    }

    private void onFetchMovieSuccess() {

        mMovieTitle.setText(fetchedMovie.getTitle());
        mMovieOverviewTv.setText(fetchedMovie.getOverview());
        mMovieAvgViewsTv.setText(String.valueOf(fetchedMovie.getVoteAverage()));

        Picasso.with(getApplicationContext())
                .load(UrlConfig.getOriginalTMDBImagePathW500(fetchedMovie.getBackDropPath()))
                .into(mMoviePosterIv);
    }


    private void showLoadingMsg() {

        mMovieTitle.setVisibility(View.GONE);
        mMovieOverviewTv.setVisibility(View.GONE);
        mMovieAvgViewsTv.setVisibility(View.GONE);
        mMoviePosterIv.setVisibility(View.GONE);

        mDetailedStatusPb.setVisibility(View.VISIBLE);
        mDetailedStatusTv.setVisibility(View.VISIBLE);
        mDetailedStatusTv.setText(getString(R.string.loading_msg));
    }

    private void showErrMsg() {

        mMovieTitle.setVisibility(View.GONE);
        mMovieOverviewTv.setVisibility(View.GONE);
        mMovieAvgViewsTv.setVisibility(View.GONE);
        mMoviePosterIv.setVisibility(View.GONE);

        mDetailedStatusPb.setVisibility(View.GONE);
        mDetailedStatusTv.setVisibility(View.VISIBLE);
        mDetailedStatusTv.setText(getString(R.string.err_msg));
    }

    private void hideLoadingMsg() {

        mMovieTitle.setVisibility(View.VISIBLE);
        mMovieOverviewTv.setVisibility(View.VISIBLE);
        mMovieAvgViewsTv.setVisibility(View.VISIBLE);
        mMoviePosterIv.setVisibility(View.VISIBLE);

        mDetailedStatusPb.setVisibility(View.GONE);
        mDetailedStatusTv.setVisibility(View.GONE);
    }


}
