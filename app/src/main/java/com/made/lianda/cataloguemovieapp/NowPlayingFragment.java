package com.made.lianda.cataloguemovieapp;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>, NowPlayingAdapter.OnMovieClickCallback {

    private NowPlayingAdapter adapter;
    private RecyclerView rvNowPlaying;
    private TextView tvNowPlaying;
    private ProgressBar pbNowPlaying;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        rvNowPlaying = view.findViewById(R.id.rv_now_playing);
        pbNowPlaying = view.findViewById(R.id.pb_now_playing);
        tvNowPlaying = view.findViewById(R.id.tv_now_playing);

        adapter = new NowPlayingAdapter(getActivity());
        adapter.notifyDataSetChanged();
        rvNowPlaying.setAdapter(adapter);
        rvNowPlaying.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnMovieClickCallback(this);

        pbNowPlaying.setVisibility(View.VISIBLE);
        tvNowPlaying.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIES, movie);
        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NowPlayingTaskLoader(getActivity());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        if (!data.isEmpty()) {
            pbNowPlaying.setVisibility(View.GONE);
            tvNowPlaying.setVisibility(View.GONE);
            rvNowPlaying.setVisibility(View.VISIBLE);
            adapter.setData(data);
        } else {
            pbNowPlaying.setVisibility(View.GONE);
            tvNowPlaying.setVisibility(View.VISIBLE);
            rvNowPlaying.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        adapter.setData(null);
    }
}