package com.example.catjam2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {Song.class},version = 2)
public abstract class CustomRoomDB extends RoomDatabase {
    private static CustomRoomDB INSTANCE;
    public abstract SongsDao songsDao();

    public static CustomRoomDB getINSTANCE(Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context,CustomRoomDB.class,"catjam_bd").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
}
