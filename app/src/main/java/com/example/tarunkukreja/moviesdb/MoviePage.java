package com.example.tarunkukreja.moviesdb;

/**
 * Created by tarunkukreja on 09/03/17.
 */

public class MoviePage {

    private String title ;
    private String overview ;
    private String language ;
    private String image ;
    private String release ;
    private boolean adult ;
    private float rating ;
    private long id ;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getRating() {
        return rating;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getRelease() {
        return release;
    }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getLanguage() {
        return language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
