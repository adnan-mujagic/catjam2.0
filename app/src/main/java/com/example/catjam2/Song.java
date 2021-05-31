package com.example.catjam2;

public class Song {
    private String name;
    private String artist;
    private int cover;

    public Song(String name, String artist, int cover) {
        this.name = name;
        this.artist = artist;
        this.cover = cover;
    }

    public Song() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getCover() {
        return cover;
    }

    public void setCover(int cover) {
        this.cover = cover;
    }


}
