package com.apps.ericksonfilipe.popularmovies;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity implements Callback<MoviesResponse>, SwipeRefreshLayout.OnRefreshListener, LoadMoreListener {

    public static final int COLUMNS = 2;
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar loadingLayout;
    private RecyclerView moviesList;
    private MoviesAdapter moviesAdapter;

    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadingLayout = (ProgressBar) findViewById(R.id.progress);
        moviesList = (RecyclerView) findViewById(R.id.movie_list_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        GridLayoutManager layoutManager = new GridLayoutManager(MoviesActivity.this, COLUMNS);
        moviesList.setHasFixedSize(true);
        moviesList.setLayoutManager(layoutManager);

        loadMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sort_by_popular) {
            SharedPreferencesManager.saveOrderBy(this, POPULAR);
            loadMovies();
            return true;
        } else if (id == R.id.sort_by_top_rated) {
            SharedPreferencesManager.saveOrderBy(this, TOP_RATED);
            loadMovies();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
        moviesAdapter.addMovies(response.body().getMovies());
        swipeRefreshLayout.setRefreshing(false);
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(Call<MoviesResponse> call, Throwable t) {
        swipeRefreshLayout.setRefreshing(false);
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        loadMovies();
    }

    @Override
    public void load() {
        loadingLayout.setVisibility(View.VISIBLE);
        MoviesAPI.getMovies(SharedPreferencesManager.getOrderBy(this), ++page, this);
    }

    private void loadMovies() {
        moviesAdapter = new MoviesAdapter(this, new ArrayList<Movie>(), this);
        moviesList.setAdapter(moviesAdapter);
        page = 1;
        MoviesAPI.getMovies(SharedPreferencesManager.getOrderBy(this), page, this);
    }
}
