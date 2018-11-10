package com.me.movieslist.network.parser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xvbp3947 on 22/04/18.
 */

public class PopularMovies {

    @SerializedName("page")
    @Expose
    private String page;

    @SerializedName("total_results")
    @Expose
    private String totalResults;

    @SerializedName("total_pages")
    @Expose
    private String totalPages;

    @SerializedName("results")
    @Expose
    private List<Movie> results = new ArrayList<Movie>();


    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}