package com.example.catjam2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catjam2.R;
import com.example.catjam2.classes.Song;
import com.example.catjam2.fragments.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecommendationDetails extends AppCompatActivity {
    String songId;
    List<Song> songs;

    Button playButton, addButton;
    TextView recommendationName, recommendationArtist;
    String songURL;
    ImageView recommendationImage;

    MediaPlayer mediaPlayer;
    int currentPosition = 0;

    public static final String EXTRA_COVER = "EXTRA_COVER";
    public static final String EXTRA_SONG_NAME = "EXTRA_SONG_NAME";
    public static final String EXTRA_ARTIST_NAME = "EXTRA_ARTIST_NAME";
    public static final String EXTRA_SONG_URL = "EXTRA_SONG_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_details);
        songId=null;
        songs=new ArrayList<>();
        playButton = findViewById(R.id.recommendation_play_button);
        addButton = findViewById(R.id.recommendation_add_button);
        recommendationArtist = findViewById(R.id.recommendation_artist_text);
        recommendationName = findViewById(R.id.recommendation_title_text);
        recommendationImage = findViewById(R.id.recommendation_image);
        recommendationImage.setImageResource(R.drawable.ic_baseline_music_note_24);
        //SET UP THE MEDIA PLAYER
        mediaPlayer = new MediaPlayer();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("recommendations");

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            songId=extras.getString(HomeFragment.EXTRA_SONG_NAME);
            songURL = extras.getString(HomeFragment.EXTRA_SONG_URL);

            recommendationName.setText(extras.getString(HomeFragment.EXTRA_SONG_NAME));
            recommendationArtist.setText(extras.getString(HomeFragment.EXTRA_ARTIST_NAME));
            //WE NEED TO RETRIEVE A LIST OF RECOMMENDATIONS FROM THE DATABASE!

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    songs.clear();
                    for(DataSnapshot songSnap : snapshot.child("songs").getChildren()){
                        Song song = songSnap.getValue(Song.class);
                        song.setCover(R.drawable.ic_baseline_music_note_24);
                        songs.add(song);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(RecommendationDetails.this, "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void playFile(View view){
        try{
            if(!mediaPlayer.isPlaying()){
                if(playButton.getText().toString().equals("Resume")){
                    mediaPlayer.seekTo(currentPosition);
                    mediaPlayer.start();
                    playButton.setText("Pause");
                } else{
                    mediaPlayer.setDataSource(songURL);
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

    public void add(View view){
        Bundle extras = getIntent().getExtras();
        Intent intent = new Intent(this,PickPlaylist.class);
        intent.putExtra(EXTRA_SONG_NAME,extras.getString(HomeFragment.EXTRA_SONG_NAME));
        intent.putExtra(EXTRA_SONG_URL,extras.getString(HomeFragment.EXTRA_SONG_URL));
        intent.putExtra(EXTRA_ARTIST_NAME,extras.getString(HomeFragment.EXTRA_ARTIST_NAME));
        intent.putExtra(EXTRA_COVER,extras.getString(HomeFragment.EXTRA_COVER));
        startActivity(intent);
    }


}