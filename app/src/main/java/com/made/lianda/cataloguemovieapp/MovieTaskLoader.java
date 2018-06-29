package com.made.lianda.cataloguemovieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieTaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {

    private ArrayList<Movie> movieArrayList;
    private boolean mHasResult =false;
    private String queryMovie;

    public MovieTaskLoader(final Context context, String queryMovie) {
        super(context);

        onContentChanged();
        this.queryMovie = queryMovie;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(movieArrayList);
    }

    @Override
    public void deliverResult(ArrayList<Movie> data) {

        movieArrayList = data;
        mHasResult = true;

        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();
        if (mHasResult){
            movieArrayList = null;
            mHasResult = false;
        }
    }

    @Override
    public ArrayList<Movie> loadInBackground() {

        SyncHttpClient client =new SyncHttpClient();

        final ArrayList<Movie> movieItemses = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/search/movie?api_key="+ BuildConfig.MY_API_KEY + "&language=en-US&query="+ queryMovie;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                Log.d("TAG", "Loader Success");

                try {

                    String result = new String(responseBody);

                    JSONObject responObject = new JSONObject(result);
                    JSONArray list = responObject.getJSONArray("results");

                    for (int i=0; i<list.length(); i++){
                        JSONObject movie = list.getJSONObject(i);
                        Movie movies = new Movie(movie);
                        movieItemses.add(movies);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                Log.d("TAG", "Loader failed");

            }
        });

        return movieItemses;
    }



}
