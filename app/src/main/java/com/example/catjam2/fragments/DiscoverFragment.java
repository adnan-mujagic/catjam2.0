package com.example.catjam2.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.catjam2.R;
import com.example.catjam2.activities.LoginActivity;
import com.example.catjam2.activities.MainActivity;
import com.example.catjam2.classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DiscoverFragment extends Fragment {

    Button logoutButton;
    ImageView profilePic;
    TextView username,email;
    public static final int SONG_ID = -1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discover,container,false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logoutButton = getView().findViewById(R.id.logout_button);
        profilePic = getView().findViewById(R.id.profile_picture);
        username = getView().findViewById(R.id.profile_username);
        email = getView().findViewById(R.id.profile_email);


        profilePic.setImageResource(R.drawable.ic_baseline_person_outline_24);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.child(MainActivity.username).getValue(User.class);
                if(user==null){
                    Toast.makeText(getContext(), "Something went wrong while contacting the database.", Toast.LENGTH_SHORT).show();
                }
                else{
                    username.setText(user.getUsername());
                    email.setText(user.getEmail());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
