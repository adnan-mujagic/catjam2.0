package com.example.catjam2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.catjam2.R;
import com.example.catjam2.activities.PlaylistDetails;
import com.example.catjam2.classes.Song;
import com.example.catjam2.fragments.PlaylistsFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SongDetails extends AppCompatActivity {

    public static final String EXTRA_COVER = "EXTRA_COVER";
    public static final String EXTRA_SONG_NAME = "EXTRA_SONG_NAME";
    public static final String EXTRA_ARTIST_NAME = "EXTRA_ARTIST_NAME";
    public static final String EXTRA_SONG_URL = "EXTRA_SONG_URL";
    public static final String EXTRA_PLAYLIST_ID = "EXTRA_PLAYLIST_ID";

    ImageView coverImage;
    TextView songName, artistName;
    Button playButton, playPreviousButton, skipButton;
    private String songUrl;
    MediaPlayer mediaPlayer;
    int currentPosition = 0;
    String playlistID = "";
    List<Song> songs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);

        coverImage = findViewById(R.id.song_details_cover);
        songName = findViewById(R.id.song_details_song_name);
        artistName = findViewById(R.id.song_details_song_artist);
        playButton = findViewById(R.id.play_button);
        playPreviousButton = findViewById(R.id.play_previous_button);
        skipButton = findViewById(R.id.skip_button);
        mediaPlayer = new MediaPlayer();
        songs = new ArrayList<>();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                songs.clear();

                for(DataSnapshot songSnap: snapshot.child(MainActivity.username).child("playlists").child(getIntent().getExtras().getString(PlaylistDetails.EXTRA_PLAYLIST_ID)).child("songs").getChildren()){
                    Song s = songSnap.getValue(Song.class);
                    s.setCover(R.drawable.ic_baseline_music_note_24);
                    songs.add(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SongDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            /*
            if(extras.getString(PlaylistsDetails.EXTRA_COVER_URL).isEmpty()){
                coverImage.setImageResource(extras.getInt(PlaylistsDetails.EXTRA_COVER));
            }
            else{
                Glide.with(this).load(extras.getString(PlaylistsDetails.EXTRA_COVER_URL)).into(coverImage);
            }*/

            if(extras.getString(PlaylistDetails.EXTRA_COVER_URL).isEmpty()){
                coverImage.setImageResource(R.drawable.ic_baseline_music_note_24);
            } else{
                Glide.with(this).load(extras.getString(PlaylistDetails.EXTRA_COVER_URL)).into(coverImage);
            }

            songUrl = extras.getString(PlaylistDetails.EXTRA_SONG_URL);
            songName.setText(extras.getString(PlaylistDetails.EXTRA_SONG_NAME));
            artistName.setText(extras.getString(PlaylistDetails.EXTRA_ARTIST_NAME));
            // coverImage.setImageResource(R.drawable.ic_baseline_music_note_24);
            playlistID = extras.getString(PlaylistDetails.EXTRA_PLAYLIST_ID);
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

    public void skipSong(View view){
        resetMediaPlayer();
        if(!playlistID.isEmpty()){
            int position = 0;

            for(Song s: songs){
                if(s.getName().equals(songName.getText().toString())){
                    // check if the current song is the last song in the list
                    // if it is the last song, then when you press skip
                    // it will play the first song in the list
                    // otherwise it will just play the next song in the list
                    if(position == songs.size() - 1){
                        position = 0;
                    } else{
                        position++;
                    }
                    break;
                }
                position++;
            }

            Song nextSong = songs.get(position);

            songUrl = nextSong.getSongUrl();
            songName.setText(nextSong.getName());
            artistName.setText(nextSong.getArtist());
            if(nextSong.getCoverUrl().isEmpty()){
                coverImage.setImageResource(R.drawable.ic_baseline_music_note_24);
            } else{
                Glide.with(this).load(nextSong.getCoverUrl()).into(coverImage);
            }

        }
    }

    public void playPreviousSong(View view){
        resetMediaPlayer();
        if(!playlistID.isEmpty()){
            int position = 0;

            for(Song s: songs){
                if(s.getName().equals(songName.getText().toString())){
                    // check if the current song is the first song in the list
                    // if it is the first song, then when you press previous
                    // it will just rewind the current song to the start
                    // otherwise it will play the previous song in the playlist
                    if(position == 0){
                        break;
                    } else{
                        position--;
                    }
                    break;
                }
                position++;
            }

            Song previousSong = songs.get(position);

            songUrl = previousSong.getSongUrl();
            songName.setText(previousSong.getName());
            artistName.setText(previousSong.getArtist());

            if(previousSong.getCoverUrl().isEmpty()){
                coverImage.setImageResource(R.drawable.ic_baseline_music_note_24);
            } else{
                Glide.with(this).load(previousSong.getCoverUrl()).into(coverImage);
            }
        }
    }

    public void resetMediaPlayer(){
        mediaPlayer.stop();
        playButton.setText("Play");
        mediaPlayer = new MediaPlayer();
    };

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