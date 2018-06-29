package com.made.lianda.cataloguemovieapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchMovieActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>, SearchMovieAdapter.OnMovieClickCallback {

    private SearchMovieAdapter adapter;
    private RecyclerView rvSearchMovie;
    private TextView tvSearchMovie;
    private ProgressBar pbSearchMovie;
    private EditText edtSearchMovie;
    private Button btnSearchMovie;

    static final String EXTRAS_QUERY = "EXTRAS_QUERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        rvSearchMovie = findViewById(R.id.rv_searchmovie);
        tvSearchMovie = findViewById(R.id.tv_searchmovie);
        pbSearchMovie = findViewById(R.id.pb_searchmovie);
        edtSearchMovie = findViewById(R.id.et_title);
        btnSearchMovie = findViewById(R.id.btn_search);

        adapter = new SearchMovieAdapter(this);
        adapter.notifyDataSetChanged();
        rvSearchMovie.setAdapter(adapter);
        rvSearchMovie.setLayoutManager(new LinearLayoutManager(this));

        btnSearchMovie.setOnClickListener(clickListener);
        adapter.setOnMovieClickCallback(this);

        String title = edtSearchMovie.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_QUERY, title);

        pbSearchMovie.setVisibility(View.VISIBLE);
        tvSearchMovie.setVisibility(View.INVISIBLE);

        getSupportLoaderManager().initLoader(1, bundle, this);

    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        String queryMovie = " ";

        if (args != null){
            queryMovie = args.getString(EXTRAS_QUERY);
        }

        return new MovieTaskLoader(this, queryMovie);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        if (!data.isEmpty()) {
           pbSearchMovie.setVisibility(View.GONE);
            tvSearchMovie.setVisibility(View.GONE);
            rvSearchMovie.setVisibility(View.VISIBLE);
            adapter.setData(data);
        } else {
            pbSearchMovie.setVisibility(View.GONE);
            tvSearchMovie.setVisibility(View.VISIBLE);
            rvSearchMovie.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        adapter.setData(null);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
       @Override
        public void onClick(View v) {
            pbSearchMovie.setVisibility(View.VISIBLE);
            tvSearchMovie.setVisibility(View.INVISIBLE);

            String title = edtSearchMovie.getText().toString();

            if (TextUtils.isEmpty(title)){
                Toast.makeText(getApplicationContext(), R.string.label_not_search, Toast.LENGTH_SHORT).show();
            }

            Bundle bundle = new Bundle();
            bundle.putString(EXTRAS_QUERY, title);

            getSupportLoaderManager().restartLoader(0, bundle, SearchMovieActivity.this);
        }
    };

    @Override
    public void onMovieClicked(Movie movie) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIES, movie);
        startActivity(intent);
    }
}
