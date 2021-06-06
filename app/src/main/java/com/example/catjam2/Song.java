package com.example.catjam2;
/*
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
*/
//@Entity(tableName = "songs")
public class Song {
    //@PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String artist;
    private int cover;
    private String songUrl;

    public Song(int id, String name, String artist){
        this.name=name;
        this.id=id;
        this.artist=artist;
    }

    //@Ignore
    public Song(int id, String name, String artist, String songUrl){
        this.name=name;
        this.id=id;
        this.artist=artist;
        this.songUrl = songUrl;
    }

   // @Ignore
    public Song(String name, String artist, int cover, String songUrl) {
        this.name = name;
        this.artist = artist;
        this.cover = cover;
        this.songUrl = songUrl;
    }
    //@Ignore
    public Song(String name, String artist){
        this.name=name;
        this.artist=artist;
    }

   // @Ignore
    public Song() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }
}
