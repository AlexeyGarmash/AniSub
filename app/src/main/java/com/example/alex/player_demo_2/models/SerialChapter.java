package com.example.alex.player_demo_2.models;

public class SerialChapter {

    private String numberChpater;
    private String movieLink;

    public  SerialChapter(String numChapter, String link){
        numberChpater = numChapter;
        movieLink = link;
    }

    public String getNumberChpater() {
        return numberChpater;
    }

    public void setNumberChpater(String numberChpater) {
        this.numberChpater = numberChpater;
    }

    public String getMovieLink() {
        return movieLink;
    }

    public void setMovieLink(String movieLink) {
        this.movieLink = movieLink;
    }
}
