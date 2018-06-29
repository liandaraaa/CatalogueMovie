package com.made.lianda.cataloguemovieapp;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movie implements Parcelable {

    private int id;
    private String title;
    private String rank;
    private String date;
    private String poster;
    private String description;

    public Movie (JSONObject object){

        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String rank = String.valueOf(object.getDouble("vote_average"));
            String date = object.getString("release_date");
            String decription = object.getString("overview");
            String poster = object.getString("poster_path");

            this.id = id;
            this.title = title;
            this.rank = rank;
            this.date = date;
            this.description = decription;
            this.poster = poster;

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        rank = in.readString();
        date = in.readString();
        poster = in.readString();
        description = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPoster() {
        return "http://image.tmdb.org/t/p/w185" + poster;
    }

    public void setPoster(String poster) {
        this.poster = "http://image.tmdb.org/t/p/w185" + poster;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(rank);
        dest.writeString(date);
        dest.writeString(poster);
        dest.writeString(description);
    }
}
