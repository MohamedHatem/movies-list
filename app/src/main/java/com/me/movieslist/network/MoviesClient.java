package com.me.movieslist.network;

import com.me.movieslist.network.parser.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface MoviesClient {

    @GET("movie/{movieId}")
    Call<Movie> getMovie(@Path("movieId") String movieId);

}
