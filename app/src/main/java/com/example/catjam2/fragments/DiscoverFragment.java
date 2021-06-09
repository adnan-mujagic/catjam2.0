package com.example.catjam2.fragments;

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

import com.example.catjam2.R;

import java.util.List;

public class DiscoverFragment extends Fragment {
    ListView listView;
    public static final int SONG_ID = -1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover,container,false);
    }
    /*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = getView().findViewById(R.id.room_list_view);
        List<Song> songs = CustomRoomDB.getINSTANCE(getContext()).songsDao().getAllLocalSongs();
        RoomSongsListAdapter adapter = new RoomSongsListAdapter(songs,getContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song song = (Song) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(),AddSongsToRoom.class);
                intent.putExtra(String.valueOf(SONG_ID),song.getId());
                startActivity(intent);
            }
        });

    }*/


}
