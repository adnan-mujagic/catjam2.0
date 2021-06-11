package com.example.catjam2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.catjam2.fragments.PlaylistsFragment;
import com.example.catjam2.R;
import com.example.catjam2.classes.Song;
import com.example.catjam2.adapters.SongListViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlaylistDetails extends AppCompatActivity {

    public static final String EXTRA_COVER = "EXTRA_COVER";
    public static final String EXTRA_SONG_NAME = "EXTRA_SONG_NAME";
    public static final String EXTRA_ARTIST_NAME = "EXTRA_ARTIST_NAME";
    public static final String EXTRA_SONG_URL = "EXTRA_SONG_URL";
    public static final String EXTRA_PLAYLIST_ID = "EXTRA_PLAYLIST_ID";
    public static final String EXTRA_COVER_URL = "EXTRA_COVER_URL";


    ValueEventListener valueEventListener;
    ImageView image;
    TextView title, description;
    ListView songsListView;
    FloatingActionButton playRandomSongButton;
    Button deletePlaylistButton;
    List<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_details);
        image = findViewById(R.id.playlist_details_image);
        title = findViewById(R.id.playlist_details_title);
        description = findViewById(R.id.playlist_details_description);
        songsListView = findViewById(R.id.song_list_view);
        playRandomSongButton = findViewById(R.id.play_random_song_button);
        deletePlaylistButton = findViewById(R.id.delete_playlist_button);

        songs = new ArrayList<>();
        SongListViewAdapter songAdapter = new SongListViewAdapter(getApplicationContext(),songs);
        songsListView.setAdapter(songAdapter);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            setTitle(extras.getString(PlaylistsFragment.EXTRA_TITLE));
            title.setText(extras.getString(PlaylistsFragment.EXTRA_TITLE));
            image.setImageResource(extras.getInt(PlaylistsFragment.EXTRA_IMAGE));
            description.setText(extras.getString(PlaylistsFragment.EXTRA_DESCRIPTION));

            songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Song song = (Song) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getBaseContext(), SongDetails.class);

                    intent.putExtra(EXTRA_COVER_URL, song.getCoverUrl());
                    intent.putExtra(EXTRA_COVER, song.getCover());
                    intent.putExtra(EXTRA_SONG_NAME, song.getName());
                    intent.putExtra(EXTRA_ARTIST_NAME, song.getArtist());
                    intent.putExtra(EXTRA_SONG_URL, song.getSongUrl());
                    intent.putExtra(EXTRA_PLAYLIST_ID, extras.getString(PlaylistsFragment.EXTRA_PLAYLIST_ID));
                    startActivity(intent);
                }
            });
        }

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                songs.clear();

                for(DataSnapshot songSnap: snapshot.child(MainActivity.username).child("playlists").child(extras.getString(PlaylistsFragment.EXTRA_PLAYLIST_ID)).child("songs").getChildren()){
                    Song s = songSnap.getValue(Song.class);
                    s.setCover(R.drawable.ic_baseline_music_note_24);
                    songs.add(s);
                }

                songAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void playRandomSong(View view){
        Random rand = new Random();
        if(songs.size() == 0){
            Toast.makeText(this, "You don't have any songs on this playlist!", Toast.LENGTH_SHORT).show();
        } else {
            int a = rand.nextInt(songs.size()-1);
            Song randSong = songs.get(a);
            Intent intent = new Intent(getBaseContext(), SongDetails.class);

            intent.putExtra(EXTRA_COVER, randSong.getCover());
            intent.putExtra(EXTRA_SONG_NAME, randSong.getName());
            intent.putExtra(EXTRA_ARTIST_NAME, randSong.getArtist());
            intent.putExtra(EXTRA_SONG_URL, randSong.getSongUrl());
            intent.putExtra(EXTRA_COVER_URL, randSong.getCoverUrl());
            intent.putExtra(EXTRA_PLAYLIST_ID, getIntent().getExtras().getString(PlaylistsFragment.EXTRA_PLAYLIST_ID));
            startActivity(intent);
        }
    }

    public void deletePlaylist(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        removeFromDatabase();
    }

    public void removeFromDatabase(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("users").child(MainActivity.username).child("playlists").child(getIntent().getExtras().getString(PlaylistsFragment.EXTRA_PLAYLIST_ID)).removeValue();
        return;
    }

    /*
    public List<Song> getSongs(){
        List<Song> songs = new ArrayList<>();
        songs.add(new Song("NAME","ARTIST",R.drawable.ic_baseline_music_note_24));
        songs.add(new Song("NAME","ARTIST",R.drawable.ic_baseline_music_note_24));
        songs.add(new Song("NAME","ARTIST",R.drawable.ic_baseline_music_note_24));
        return songs;

    }*/
}