/*package com.example.catjam2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class RoomSongsListAdapter extends BaseAdapter {

    private List<Song> songs;
    private Context context;

    public RoomSongsListAdapter(List<Song> songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return songs.get(position);

    }

    @Override
    public long getItemId(int position) {
        return songs.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.room_db_song_item, parent, false);

        Song song = songs.get(position);

        TextView rName, rArtist;
        rName = convertView.findViewById(R.id.room_song_name);
        rArtist = convertView.findViewById(R.id.room_song_artist);

        rName.setText(song.getName());
        rArtist.setText(song.getArtist());

        return convertView;
    }
}*/
