package com.made.lianda.cataloguemovieapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>>, UpcomingAdapter.OnMovieClickCallback {

    private UpcomingAdapter adapter;
    private RecyclerView rvUpcoming;
    private TextView tvUpcoming;
    private ProgressBar pbUpcoming;

    public UpcomingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getSupportLoaderManager().initLoader(2, null, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        rvUpcoming = view.findViewById(R.id.rv_upcoming);
        tvUpcoming = view.findViewById(R.id.tv_upcoming);
        pbUpcoming = view.findViewById(R.id.pb_upcoming);

        adapter = new UpcomingAdapter(getActivity());
        adapter.notifyDataSetChanged();
        rvUpcoming.setAdapter(adapter);
        rvUpcoming.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnMovieClickCallback(this);

        pbUpcoming.setVisibility(View.VISIBLE);
        tvUpcoming.setVisibility(View.INVISIBLE);

        return view;
    }

    @NonNull
    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, @Nullable Bundle args) {
        return new UpcomingTaskLoader(getActivity());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        if (!data.isEmpty()) {
            pbUpcoming.setVisibility(View.GONE);
            tvUpcoming.setVisibility(View.GONE);
            rvUpcoming.setVisibility(View.VISIBLE);
            adapter.setData(data);
        } else {
            pbUpcoming.setVisibility(View.GONE);
            tvUpcoming.setVisibility(View.VISIBLE);
            rvUpcoming.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movie>> loader) {
        adapter.setData(null);
    }

    @Override
    public void onMovieClicked(Movie movie) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIES, movie);
        startActivity(intent);
    }
}
