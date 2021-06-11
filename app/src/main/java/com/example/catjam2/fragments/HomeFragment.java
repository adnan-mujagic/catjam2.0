package com.example.catjam2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.catjam2.R;
import com.example.catjam2.activities.RecommendationDetails;
import com.example.catjam2.adapters.RecommendationListViewAdapter;
import com.example.catjam2.classes.Song;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ListView recommendationsList;
    public static final String EXTRA_COVER = "EXTRA_COVER";
    public static final String EXTRA_SONG_NAME = "EXTRA_SONG_NAME";
    public static final String EXTRA_ARTIST_NAME = "EXTRA_ARTIST_NAME";
    public static final String EXTRA_SONG_URL = "EXTRA_SONG_URL";
    public static final String EXTRA_PLAYLIST_ID = "EXTRA_PLAYLIST_ID";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recommendationsList = view.findViewById(R.id.recommendations_list);

        List<Song> songs = new ArrayList<>();
        RecommendationListViewAdapter songsAdapter = new RecommendationListViewAdapter(songs,getContext());
        recommendationsList.setAdapter(songsAdapter);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("recommendations");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                songs.clear();
                for(DataSnapshot ds: snapshot.child("songs").getChildren()){

                    Song song = ds.getValue(Song.class);
                    song.setCover(R.drawable.ic_baseline_music_note_24);
                    songs.add(song);
                }
                songsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        recommendationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song = (Song) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), RecommendationDetails.class);
                intent.putExtra(EXTRA_COVER, song.getCover());
                intent.putExtra(EXTRA_SONG_NAME,song.getName());
                intent.putExtra(EXTRA_ARTIST_NAME, song.getArtist());
                intent.putExtra(EXTRA_SONG_URL, song.getSongUrl());
                startActivity(intent);
            }
        });
    }

}
