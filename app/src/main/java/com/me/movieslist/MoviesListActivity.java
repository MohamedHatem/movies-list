package com.me.movieslist;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.me.movieslist.adapter.MoviesAdapter;
import com.me.movieslist.network.MoviesClient;
import com.me.movieslist.network.ServiceGenerator;
import com.me.movieslist.network.parser.Movie;
import com.me.movieslist.network.parser.PopularMovies;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoviesListActivity extends AppCompatActivity {

    public static final String TAG = "MoviesListActivity";

    @BindView(R.id.movies_recycler_view)
    RecyclerView mMoviesRecyclerView;

    @BindView(R.id.lst_status_tv)
    TextView mLstStatusTv;

    @BindView(R.id.lst_status_pb)
    ProgressBar mLstStatusPb;

    private MoviesAdapter mMoviesAdapter;
    private RecyclerView.LayoutManager mMoviesLayoutManager;
    private List<Movie> moviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);

        ButterKnife.bind(this);

        // use a linear layout manager
        mMoviesLayoutManager = new LinearLayoutManager(this);
        mMoviesRecyclerView.setLayoutManager(mMoviesLayoutManager);

        // specify an adapter
        moviesList = new ArrayList<>();
        mMoviesAdapter = new MoviesAdapter(getApplicationContext(), moviesList);
        mMoviesRecyclerView.setAdapter(mMoviesAdapter);


        mMoviesAdapter.setListener(new MoviesAdapter.MoviesViewHolderClicks() {
            @Override
            public void onItemClick(View v, int position) {
                onListItemClick(position);
            }
        });

        fetchPopularMovies();

    }

    public void onListItemClick(int pos) {
        Bundle movieExtras = new Bundle();
        movieExtras.putString(MovieDetailActivity.MOVIE_ID_ARG, moviesList.get(pos).getMovieId());
        MovieDetailActivity.launchActivity(this, movieExtras);
    }

    private void fetchPopularMovies() {
        MoviesClient moviesClient = ServiceGenerator.createService(MoviesClient.class);
        Observable<PopularMovies> movie = moviesClient.getPopularMovies("1");
        movie.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<PopularMovies>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                        showLoadingMsg();
                    }

                    @Override
                    public void onNext(PopularMovies popMoviesList) {
                        moviesList = popMoviesList.getResults();
                        Log.d(TAG, "onNext");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError");
                        showErrMsg();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                        mMoviesAdapter.setMoviesList(moviesList);
                        mMoviesAdapter.notifyDataSetChanged();
                        hideLoadingMsg();
                    }
                });
    }

    private void showLoadingMsg() {
        mMoviesRecyclerView.setVisibility(View.GONE);
        mLstStatusTv.setVisibility(View.VISIBLE);
        mLstStatusPb.setVisibility(View.VISIBLE);

        mLstStatusTv.setText(getString(R.string.loading_msg));
    }

    private void showErrMsg() {
        mMoviesRecyclerView.setVisibility(View.GONE);
        mLstStatusPb.setVisibility(View.GONE);

        mLstStatusTv.setVisibility(View.VISIBLE);
        mLstStatusTv.setText(getString(R.string.err_msg));
    }

    private void hideLoadingMsg() {
        mMoviesRecyclerView.setVisibility(View.VISIBLE);
        mLstStatusTv.setVisibility(View.GONE);
        mLstStatusPb.setVisibility(View.GONE);
    }
}
