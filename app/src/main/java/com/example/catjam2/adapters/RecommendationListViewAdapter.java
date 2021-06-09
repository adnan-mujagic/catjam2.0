package com.example.catjam2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.catjam2.R;
import com.example.catjam2.classes.Song;

import java.util.List;

public class RecommendationListViewAdapter extends BaseAdapter {

    public static final String SONG_NAME="SONG_NAME";
    private List<Song> songs;
    private Context context;

    public RecommendationListViewAdapter(List<Song> songs, Context context) {
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
        Song song = songs.get(position);

        convertView = inflater.inflate(R.layout.custom_recommendation_item,parent,false);

        TextView name, artist;
        name = convertView.findViewById(R.id.recommendation_title);
        artist= convertView.findViewById(R.id.recommmendation_artist);


        name.setText(song.getName());
        artist.setText(song.getArtist());

        return convertView;
    }
}
