package com.me.movieslist.config;


public class UrlConfig {

    public static  String BASE_URL = "https://api.themoviedb.org/3/";


    public static String getOriginalTMDBImagePath(String posterPath) {
        return "https://image.tmdb.org/t/p/original" + posterPath;
    }
}
