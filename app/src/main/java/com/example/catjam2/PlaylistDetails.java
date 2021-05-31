package com.example.catjam2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDetails extends AppCompatActivity {
    ImageView image;
    TextView title, description;
    ListView songsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_details);
        image = findViewById(R.id.playlist_details_image);
        title = findViewById(R.id.playlist_details_title);
        description = findViewById(R.id.playlist_details_description);
        songsView = findViewById(R.id.song_list_view);
        List<Song> songs = new ArrayList<>();
        SongListViewAdapter songAdapter = new SongListViewAdapter(getApplicationContext(),songs);
        songsView.setAdapter(songAdapter);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            setTitle(extras.getString(PlaylistsFragment.EXTRA_TITLE));
            title.setText(extras.getString(PlaylistsFragment.EXTRA_TITLE));
            image.setImageResource(extras.getInt(PlaylistsFragment.EXTRA_IMAGE));
            description.setText(extras.getString(PlaylistsFragment.EXTRA_DESCRIPTION));
        }


        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                songs.clear();
                for(DataSnapshot songSnap : snapshot.child(MainActivity.username).child("playlists").child("p1").child("songs").getChildren()){
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

    public List<Song> getSongs(){
        List<Song> songs = new ArrayList<>();
        songs.add(new Song("NAME","ARTIST",R.drawable.ic_baseline_music_note_24));
        songs.add(new Song("NAME","ARTIST",R.drawable.ic_baseline_music_note_24));
        songs.add(new Song("NAME","ARTIST",R.drawable.ic_baseline_music_note_24));
        return songs;

    }
}