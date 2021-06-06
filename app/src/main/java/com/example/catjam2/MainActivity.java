package com.example.catjam2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    public static String username="USER";
    public static String song_name="Hello";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        viewPager = findViewById(R.id.fragment_container);
        setUpAdapter(viewPager);
        username=getIntent().getExtras().getString(LoginActivity.USERNAME);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Log.d("debug","item clicked"+item.getItemId());
            switch (item.getItemId()){
                case R.id.nav_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.nav_playlists:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.nav_discover:
                    viewPager.setCurrentItem(2);
                    return true;
                default:
                    return false;
            }
        }
    };

    private void setUpAdapter(ViewPager viewPager){
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPageAdapter.addFragment(new HomeFragment());
        viewPageAdapter.addFragment(new PlaylistsFragment());
        viewPageAdapter.addFragment(new DiscoverFragment());
        viewPager.setAdapter(viewPageAdapter);
    }

    public void createPlaylist(View view){
        Intent intent = new Intent(getBaseContext(),CreatePlaylist.class);
        startActivity(intent);
    }
/*
    private List<Playlist> getPlaylists(){
        List<Playlist> playlistsList = new ArrayList<>();
        playlistsList.add(new Playlist(R.drawable.playlist1, "Playlist1", "Jazz"));
        return playlistsList;
    }*/
    /*
    public void addSong(View view){
        Intent intent = new Intent(this,AddSongsToRoom.class);
        startActivity(intent);
    }*/
}