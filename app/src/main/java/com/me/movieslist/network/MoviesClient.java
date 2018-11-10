package com.me.movieslist.network;

import com.me.movieslist.network.parser.Movie;
import com.me.movieslist.network.parser.PopularMovies;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MoviesClient {

    @GET("movie/{movieId}")
    Observable<Movie> getMovie(@Path("movieId") String movieId);

    @GET("movie/popular")
    Observable<PopularMovies> getPopularMovies(@Query("page") String page);
}
