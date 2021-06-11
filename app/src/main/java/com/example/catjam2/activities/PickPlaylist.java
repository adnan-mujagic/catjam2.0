package com.example.catjam2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.catjam2.R;
import com.example.catjam2.adapters.PlaylistsListViewAdapter;
import com.example.catjam2.classes.Playlist;
import com.example.catjam2.classes.Song;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PickPlaylist extends AppCompatActivity {
    String songName,artistName,songUrl;
    int songCover;

    ListView playlistsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_playlist);
        Bundle extras = getIntent().getExtras();
        playlistsListView = findViewById(R.id.playlists_to_pick_from);

        List<Playlist> playlists = new ArrayList<>();
        PlaylistsListViewAdapter playlistsListViewAdapter = new PlaylistsListViewAdapter(playlists, getApplicationContext());
        playlistsListView.setAdapter(playlistsListViewAdapter);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                playlists.clear();
                for(DataSnapshot ds: snapshot.child(MainActivity.username).child("playlists").getChildren()){

                    Playlist p = ds.getValue(Playlist.class);
                    p.setImageResId(R.drawable.ic_baseline_queue_music_24);
                    p.setPlaylistId(ds.getKey());
                    playlists.add(p);
                }
                playlistsListViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });



        if(extras!=null){
            songName = extras.getString(RecommendationDetails.EXTRA_SONG_NAME);
            artistName = extras.getString(RecommendationDetails.EXTRA_ARTIST_NAME);
            songUrl = extras.getString(RecommendationDetails.EXTRA_SONG_URL);
            songCover = extras.getInt(RecommendationDetails.EXTRA_COVER);

            playlistsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Playlist p = (Playlist) parent.getItemAtPosition(position);
                    ref.child(MainActivity.username).child("playlists").child(p.getTitle()).child("songs").child(songName).setValue(new Song(songName,artistName,123,songUrl));
                    Toast.makeText(PickPlaylist.this, "Song added to "+p.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            });
        }




    }
}