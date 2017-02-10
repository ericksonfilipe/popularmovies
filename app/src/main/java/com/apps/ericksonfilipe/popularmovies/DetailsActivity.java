package com.apps.ericksonfilipe.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.apps.ericksonfilipe.popularmovies.MoviesAdapter.IMAGE_BASE_URL;
import static com.apps.ericksonfilipe.popularmovies.MoviesAdapter.MOVIE;

public class DetailsActivity extends AppCompatActivity {

    private TextView title;
    private ImageView poster;
    private TextView plotSynopsis;
    private TextView rating;
    private TextView releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        title = (TextView) findViewById(R.id.movie_title);
        poster = (ImageView) findViewById(R.id.movie_poster_iv);
        plotSynopsis = (TextView) findViewById(R.id.plot_synopsis);
        rating = (TextView) findViewById(R.id.movie_rating);
        releaseDate = (TextView) findViewById(R.id.movie_release_date);

        Movie movie = getIntent().getExtras().getParcelable(MOVIE);
        title.setText(movie.getOriginalTitle());
        Picasso.with(this).load(IMAGE_BASE_URL + movie.getPosterPath()).into(poster);
        plotSynopsis.setText(movie.getOverview());
        rating.setText(getString(R.string.rating) + String.valueOf(movie.getVoteAverage()));
        releaseDate.setText(getString(R.string.release_date) + movie.getReleaseDate());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
