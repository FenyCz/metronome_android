package com.example.metronome;

import android.media.AudioManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.metronome.fragment.FragmentPageAdapter;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {

    // default tempo and playername and default tab to open
    private Integer curBpm = 100;
    private String playlistName = "";
    private Integer tab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set media volume in whole activity
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);


        // if call from songlist activity, set tempo of song, if call from playlist insert playlistname
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            curBpm = bundle.getInt("bpm");
            playlistName = bundle.getString("playlist_name");
            tab = bundle.getInt("tab");
        }

        //view pager - OLD METHOD
        /*SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        viewPager.setCurrentItem(tab);
        tabs.setupWithViewPager(viewPager);*/

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPageAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(fragmentPagerAdapter);
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