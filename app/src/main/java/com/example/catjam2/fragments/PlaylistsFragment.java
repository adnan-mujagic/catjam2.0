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

import com.example.catjam2.classes.Playlist;
import com.example.catjam2.adapters.PlaylistsListViewAdapter;
import com.example.catjam2.R;
import com.example.catjam2.activities.MainActivity;
import com.example.catjam2.activities.PlaylistDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class PlaylistsFragment extends Fragment {
    private ListView listView;
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";
    public static final String EXTRA_PLAYLIST_ID = "EXTRA_PLAYLIST_ID";
    //public static final String SONG_KEY ="SONG_KEY";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlists, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list_view_container);

        List<Playlist> playlists = new ArrayList<>();
        PlaylistsListViewAdapter playlistsListViewAdapter = new PlaylistsListViewAdapter(playlists, getActivity());
        listView.setAdapter(playlistsListViewAdapter);

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
                Toast.makeText(getContext(), "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Playlist playlists = (Playlist) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), PlaylistDetails.class);

                intent.putExtra(EXTRA_IMAGE, playlists.getImageResId());
                intent.putExtra(EXTRA_PLAYLIST_ID, playlists.getPlaylistId());
                intent.putExtra(EXTRA_TITLE, playlists.getTitle());
                intent.putExtra(EXTRA_DESCRIPTION, playlists.getDescription());

                // TODO: 27. 5. 2021. put song array into intent here
                startActivity(intent);

            }
        });
        return view;
    }





}
