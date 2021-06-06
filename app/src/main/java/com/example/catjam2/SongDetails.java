package com.example.catjam2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class SongDetails extends AppCompatActivity {

    ImageView coverImage;
    TextView songName, artistName;
    Button playButton;
    private String songUrl;
    MediaPlayer mediaPlayer;
    int currentPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);

        coverImage = findViewById(R.id.song_details_cover);
        songName = findViewById(R.id.song_details_song_name);
        artistName = findViewById(R.id.song_details_song_artist);
        playButton = findViewById(R.id.play_button);
        mediaPlayer = new MediaPlayer();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            /*
            if(extras.getString(PlaylistsDetails.EXTRA_COVER_URL).isEmpty()){
                coverImage.setImageResource(extras.getInt(PlaylistsDetails.EXTRA_COVER));
            }
            else{
                Glide.with(this).load(extras.getString(PlaylistsDetails.EXTRA_COVER_URL)).into(coverImage);
            }*/
            songUrl = extras.getString(PlaylistDetails.EXTRA_SONG_URL);
            songName.setText(extras.getString(PlaylistDetails.EXTRA_SONG_NAME));
            artistName.setText(extras.getString(PlaylistDetails.EXTRA_ARTIST_NAME));
            coverImage.setImageResource(R.drawable.ic_baseline_music_note_24);
        }
    }

    // TODO: 28. 5. 2021. make this function for play button
    public void playFile(View view){
        try{
            if(!mediaPlayer.isPlaying()){
                if(playButton.getText().toString().equals("Resume")){
                    mediaPlayer.seekTo(currentPosition);
                    mediaPlayer.start();
                    playButton.setText("Pause");
                } else{
                    mediaPlayer.setDataSource(songUrl);
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                            playButton.setText("Pause");
                        }
                    });
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer = new MediaPlayer();
                            playButton.setText("Play");
                            mediaPlayer.seekTo(0);
                        }
                    });
                    mediaPlayer.prepareAsync();
                }
            } else{
                if(playButton.getText().toString().equals("Pause")) {
                    currentPosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                    playButton.setText("Resume");
                }
            }



        } catch(IOException e){
            e.printStackTrace();
        }
    }
}