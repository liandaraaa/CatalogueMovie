package com.made.lianda.cataloguemovieapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NowPlayingTaskLoader extends android.support.v4.content.AsyncTaskLoader<ArrayList<Movie>> {

    private ArrayList<Movie> movieArrayList;
    private boolean mHasResult =false;
    private static final String TAG = NowPlayingTaskLoader.class.getSimpleName();

    public NowPlayingTaskLoader(final Context context) {
        super(context);

        onContentChanged();
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

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+ BuildConfig.MY_API_KEY+"&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();

                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

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

            }
        });

        return movieItemses;
    }
}
