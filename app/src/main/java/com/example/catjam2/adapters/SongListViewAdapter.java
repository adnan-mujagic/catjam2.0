package com.example.catjam2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.catjam2.R;
import com.example.catjam2.activities.PlaylistDetails;
import com.example.catjam2.classes.Song;

import java.util.ArrayList;
import java.util.List;

public class SongListViewAdapter extends BaseAdapter {
    Context context;
    List<Song> songs;

    public SongListViewAdapter(Context context, List<Song> songs) {
        this.context = context;
        this.songs = songs;
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
        return songs.indexOf(songs.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView=inflater.inflate(R.layout.custom_song_item,parent,false);
        Song song = songs.get(position);

        ImageView cover = convertView.findViewById(R.id.song_cover);
        TextView name = convertView.findViewById(R.id.song_name);
        TextView artist = convertView.findViewById(R.id.song_artist);

        if(song.getCoverUrl().isEmpty()){
            cover.setImageResource(song.getCover());
        } else{
            Glide.with(context).load(song.getCoverUrl()).into(cover);
        }

        name.setText(song.getName());
        artist.setText(song.getArtist());

        return convertView;
    }
}
