package com.example.catjam2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SongsDao {
    @Query("SELECT * from songs")
    List<Song> getAllLocalSongs();

    @Insert
    void addSong(Song song);

    @Delete
    void deleteSong(Song song);

    @Update
    void updateSong(Song song);

    @Query("SELECT * FROM songs WHERE id=:id")
    Song getSongById(int id);
}
