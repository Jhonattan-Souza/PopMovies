package com.example.android.popmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    private String poster_path;
    private String overview;
    private String release_date;
    private int id;
    private String title;
    private double vote_average;

    public Movie() {
    }

    private Movie(Parcel in) {
        poster_path = in.readString();
        id = in.readInt();
        title = in.readString();
        release_date = in.readString();
        overview = in.readString();
        vote_average = in.readDouble();
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date.substring(0, 4);
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster_path);
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(release_date);
        parcel.writeString(overview);
        parcel.writeDouble(vote_average);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
