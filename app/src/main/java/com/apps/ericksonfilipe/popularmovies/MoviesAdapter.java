package com.apps.ericksonfilipe.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    public static final String MOVIE = "movie";
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500/";

    private final Context context;
    private List<Movie> movies;
    private LoadMoreListener loadMoreListener;

    public MoviesAdapter(Context context, List<Movie> movies, LoadMoreListener loadMoreListener) {
        this.context = context;
        this.movies = movies;
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra(MOVIE, movie);
                context.startActivity(intent);
            }
        });
        Picasso.with(context).load(IMAGE_BASE_URL + movie.getPosterPath()).into(holder.moviePoster);
        if (position >= getItemCount() -1) {
            loadMoreListener.load();
        }
    }

    @Override
    public int getItemCount() {
        if (movies != null) {
            return movies.size();
        }
        return 0;
    }

    public void addMovies(List<Movie> movies) {
        if (this.movies != null && movies!= null && !movies.isEmpty()) {
            int begin = this.movies.size();
            this.movies.addAll(movies);
            int end = this.movies.size() - 1;
            notifyItemRangeInserted(begin, end);
        }
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView moviePoster;

        public MovieViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.movie_card_view);
            moviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);

            cardView.setCardElevation(4);
        }
    }
}
