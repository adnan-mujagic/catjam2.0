package com.example.catjam2.classes;



import androidx.annotation.NonNull;

import com.example.catjam2.classes.Playlist;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public abstract class DatabaseUtils {
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static DatabaseReference userRef = db.getReference("users");
    private static ArrayList<Playlist> playlists;

    public static List<Playlist> getUserPlaylists(String username){

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                playlists = snapshot.child(username).child("playlists").getValue(ArrayList.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return  playlists;
    }

}
