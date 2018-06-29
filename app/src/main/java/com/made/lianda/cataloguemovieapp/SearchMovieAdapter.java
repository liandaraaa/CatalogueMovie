package com.made.lianda.cataloguemovieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private OnMovieClickCallback onMovieClickCallback;

    public OnMovieClickCallback getOnMovieClickCallback() {
        return onMovieClickCallback;
    }

    public void setOnMovieClickCallback(OnMovieClickCallback onMovieClickCallback) {
        this.onMovieClickCallback = onMovieClickCallback;
    }

    public SearchMovieAdapter(Context context) {
        this.mContext = context;

        mInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArrayList<Movie> getMovieArrayList() {
        return movieArrayList;
    }

    public void setData(ArrayList<Movie> movies){
        movieArrayList = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_list_item, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(getMovieArrayList().get(position));
    }


    @Override
    public int getItemCount() {
        return getMovieArrayList().size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView rank;
        TextView releaseDate;
        ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);

            title =(TextView) itemView.findViewById(R.id.tv_list_item_title);
            rank = (TextView) itemView.findViewById(R.id.tv_list_item_rank);
            releaseDate = (TextView) itemView.findViewById(R.id.tv_list_item_date);
            poster = (ImageView) itemView.findViewById(R.id.iv_list_item_image);
        }

        public void bind(Movie movie){
            Glide.with(itemView).load(movie.getPoster()).into(poster);
            title.setText(movie.getTitle());
            rank.setText(movie.getRank());
            releaseDate.setText(movie.getDate());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnMovieClickCallback().onMovieClicked(getMovieArrayList().get(getAdapterPosition()));
                }
            });
        }
    }
    public interface OnMovieClickCallback{
        void onMovieClicked(Movie movie);
    }

}
