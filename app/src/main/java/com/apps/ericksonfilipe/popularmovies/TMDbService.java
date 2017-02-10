package com.apps.ericksonfilipe.popularmovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDbService {

    @GET("movie/{orderBy}")
    Call<MoviesResponse> listMovies(
            @Path("orderBy") String orderBy,
            @Query("page") int page,
            @Query("api_key") String apiKey);
}
