package com.apps.ericksonfilipe.popularmovies;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesAPI {

    private final static String BASE_URL = "http://api.themoviedb.org/3/";
    private final static String API_KEY = "";

    private static TMDbService endpoint;

    public static TMDbService getInstance() {
        if (endpoint == null) {
            Retrofit retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            endpoint = retrofit.create(TMDbService.class);
        }
        return endpoint;
    }

    public static void getMovies(String orderBy, int page, Callback<MoviesResponse> moviesCallback) {
        Call<MoviesResponse> call = getInstance().listMovies(orderBy,page, API_KEY);
        call.enqueue(moviesCallback);
    }
}
