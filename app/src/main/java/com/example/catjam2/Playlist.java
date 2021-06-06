package com.example.catjam2;

import com.google.firebase.database.PropertyName;

public class Playlist {

    private String playlistId;
    private int imageResId;
    private String title;
    private String description;

    public Playlist(int imageResId, String title, String description, String playlistId) {
        this.imageResId = imageResId;
        this.title = title;
        this.description = description;
        this.playlistId = playlistId;
    }

    public Playlist() {

    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
