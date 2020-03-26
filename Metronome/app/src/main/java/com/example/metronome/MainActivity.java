package com.example.metronome;

import android.os.Bundle;

import com.example.metronome.fragment.MetronomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toolbar;

import com.example.metronome.ui.main.SectionsPagerAdapter;


public class MainActivity extends AppCompatActivity {

    // default tempo and playername and default tab to open
    private Integer curBpm = 100;
    private String playlistName = "";
    private Integer tab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // if call from songlist activity, set tempo of song, if call from playlist insert playlistname
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            curBpm = bundle.getInt("bpm");
            playlistName = bundle.getString("playlist_name");
            tab = bundle.getInt("tab");
        }

        //view pager
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        viewPager.setCurrentItem(tab);
        tabs.setupWithViewPager(viewPager);
    }

    // function for fragment metronome
    public int getResult(){
        return this.curBpm;
    }

    public String getPlaylistName(){ return this.playlistName; }
}