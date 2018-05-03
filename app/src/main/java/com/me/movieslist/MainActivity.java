package com.me.movieslist;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle movieExtras = new Bundle();
        movieExtras.putString(MovieDetailActivity.MOVIE_ID_ARG, "550");
        MovieDetailActivity.launchActivity(this, movieExtras);
    }
}
