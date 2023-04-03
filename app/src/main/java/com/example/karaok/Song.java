package com.example.karaok;

public class Song {

    private String name;
    private String artist;
    private String duration;
    private float rating;

    public Song(String name, String artist, String duration, float rating) {
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getDuration() {
        return duration;
    }

    public float getRating() {
        return rating;
    }
}

