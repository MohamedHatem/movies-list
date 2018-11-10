package com.me.movieslist.config;


public class UrlConfig {

    public static  String BASE_URL = "https://api.themoviedb.org/3/";


    public static String getOriginalTMDBImagePath(String posterPath) {
        return "https://image.tmdb.org/t/p/original" + posterPath;
    }
    public static String getOriginalTMDBImagePathW500(String posterPath) {
        return "https://image.tmdb.org/t/p/w500" + posterPath;
    }
}
