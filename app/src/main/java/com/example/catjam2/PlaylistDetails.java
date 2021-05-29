package com.example.catjam2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaylistDetails extends AppCompatActivity {
    ImageView image;
    TextView title, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_details);
        image = findViewById(R.id.playlist_details_image);
        title = findViewById(R.id.playlist_details_title);
        description = findViewById(R.id.playlist_details_description);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            setTitle(extras.getString(PlaylistsFragment.EXTRA_TITLE));
            title.setText(extras.getString(PlaylistsFragment.EXTRA_TITLE));
            image.setImageResource(extras.getInt(PlaylistsFragment.EXTRA_IMAGE));
            description.setText(extras.getString(PlaylistsFragment.EXTRA_DESCRIPTION));
        }
    }
}