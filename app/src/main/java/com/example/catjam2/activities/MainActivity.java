package com.example.catjam2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.catjam2.fragments.DiscoverFragment;
import com.example.catjam2.fragments.HomeFragment;
import com.example.catjam2.fragments.PlaylistsFragment;
import com.example.catjam2.R;
import com.example.catjam2.adapters.ViewPageAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

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

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            if(extras.getString(LoginActivity.USERNAME)!=null){
                username=extras.getString(LoginActivity.USERNAME);
            }
            else if(extras.getString(RegisterActivity.USERNAME) != null) {
                username = extras.getString(RegisterActivity.USERNAME);
            }
        }


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
        Intent intent = new Intent(getBaseContext(), CreatePlaylist.class);
        startActivity(intent);
    }

    public void logout(View view){
        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "false");
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}