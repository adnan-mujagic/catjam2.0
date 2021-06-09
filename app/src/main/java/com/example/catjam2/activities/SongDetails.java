package com.example.catjam2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catjam2.R;
import com.example.catjam2.activities.PlaylistDetails;

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

    public void showDetailsOnGenius(View view){
        String aName = artistName.getText().toString();
        String sName = songName.getText().toString();
        Intent intent = new Intent(Intent.ACTION_VIEW);

        String url = "https://genius.com/";
        String[] artistWords = aName.split(" ");
        String[] songWords = sName.split(" ");
        for(String word : artistWords){
            url+=word+"-";
        }
        for(String word : songWords){
            url+=word+"-";
        }
        url+="lyrics";

        intent.setData(Uri.parse(url));

        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }


    }

    public void share(View view){
        String aName = artistName.getText().toString();
        String sName = songName.getText().toString();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello, I am listening to "+sName+" by "+aName+" on CatJam! It is Amazing!");
        sendIntent.setType("text/plain");

        if(sendIntent.resolveActivity(getPackageManager())!=null){
            startActivity(sendIntent);
        }
    }
}