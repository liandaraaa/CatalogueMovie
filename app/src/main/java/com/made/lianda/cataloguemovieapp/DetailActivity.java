package com.made.lianda.cataloguemovieapp;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIES = "movies";
    private Movie movie;
    private ImageView poster;
    private TextView title, rank, date, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movie = getIntent().getParcelableExtra(MOVIES);
        setTitle(movie.getTitle());

        poster = findViewById(R.id.detail_poster);
        rank = findViewById(R.id.tv_detail_rank);
        date = findViewById(R.id.tv_detail_date);
        description = findViewById(R.id.tv_detail_description);
        title = findViewById(R.id.tv_detail_title);


        Glide.with(this).load(movie.getPoster()).into(poster);
        rank.setText(movie.getRank());
        date.setText(movie.getDate());
        title.setText(movie.getTitle());
        description.setText(movie.getDescription());


    }
}
