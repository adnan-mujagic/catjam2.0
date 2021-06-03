package com.example.catjam2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddSongsToRoom extends AppCompatActivity {
    Button save, back;
    EditText addedTitle, addedArtist;
    Song song;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_songs_to_room);

        save = findViewById(R.id.save);
        back = findViewById(R.id.back);

        song=null;

        addedArtist = findViewById(R.id.add_room_song_artist);
        addedTitle = findViewById(R.id.add_room_song_title);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            song = CustomRoomDB.getINSTANCE(getApplicationContext()).songsDao().getSongById(extras.getInt(String.valueOf(DiscoverFragment.SONG_ID)));
            addedArtist.setText(song.getArtist());
            addedTitle.setText(song.getName());
            save.setText("Update");
            back.setText("Delete");
        }

    }

    public void action(View view){
        Intent intent = new Intent(getApplicationContext(),DiscoverFragment.class);
        if(view.getId()==R.id.save){
            if(song!=null){
                CustomRoomDB.getINSTANCE(getApplicationContext()).songsDao().updateSong(new Song(song.getId(),addedTitle.getText().toString(),addedArtist.getText().toString()));
                startActivity(intent);
            }
            else{
                CustomRoomDB.getINSTANCE(getApplicationContext()).songsDao().addSong(new Song(addedTitle.getText().toString(),addedArtist.getText().toString()));
                startActivity(intent);
            }
        }
        else if(view.getId()==R.id.back){
            if(song!=null){
                CustomRoomDB.getINSTANCE(getApplicationContext()).songsDao().deleteSong(song);
                startActivity(intent);
            }
            else{
                startActivity(intent);
            }
        }
    }
}