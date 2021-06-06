package com.example.catjam2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePlaylist extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference("users");
    EditText name, desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_playlist);

        name = findViewById(R.id.playlist_name);
        desc = findViewById(R.id.playlist_description);
    }

    public void createPlaylistOrCancel(View view){
        String pName, pDesc;
        pName=name.getText().toString();
        pDesc=desc.getText().toString();
        if(view.getId()==R.id.add_playlist){
            ref.child(MainActivity.username).child("playlists").child(pName).setValue(new Playlist(pName,pDesc));
            Intent intent = new Intent(this,PlaylistsFragment.class);
            startActivity(intent);
            Toast.makeText(this, "Playlist "+pName+" successfully added!", Toast.LENGTH_SHORT).show();
        }
        else if(view.getId()==R.id.cancel_add_playlist){
            Intent intent = new Intent(this,PlaylistsFragment.class);
            startActivity(intent);
        }
    }
}