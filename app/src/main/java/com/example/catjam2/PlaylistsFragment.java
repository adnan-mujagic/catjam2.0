package com.example.catjam2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class PlaylistsFragment extends Fragment {
    private ListView listView;
    String[] months;
    List<Playlist> playlists;
    public static final String EXTRA_IMAGE = "EXTRA_IMAGE";
    public static final String EXTRA_TITLE = "EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlists, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list_view_container);

        PlaylistsListViewAdapter playlistsListViewAdapter = new PlaylistsListViewAdapter(getPlaylists(), getActivity());
        listView.setAdapter(playlistsListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Playlist playlists = (Playlist) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), PlaylistDetails.class);
                intent.putExtra(EXTRA_IMAGE, playlists.getImageResId());
                intent.putExtra(EXTRA_TITLE, playlists.getTitle());
                intent.putExtra(EXTRA_DESCRIPTION, playlists.getDescription());
                // TODO: 27. 5. 2021. put song array into intent here
                startActivity(intent);

            }
        });
/*
        PlaylistsListViewAdapter playlistsListViewAdapter = PlaylistsListViewAdapter(playlists, )
*/
        return view;
    }
    private List<Playlist> getPlaylists(){
        List<Playlist> playlistsList = new ArrayList<>();
        playlistsList.add(new Playlist(R.drawable.playlist1, "Playlist1", "Jazz"));
        playlistsList.add(new Playlist(R.drawable.playlist1, "Playlist2", "Chill"));
        playlistsList.add(new Playlist(R.drawable.playlist3, "Playlist3", "Top50"));
        return playlistsList;
    }
}
