package com.example.metronome.playlistdatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.metronome.R;
import com.example.metronome.songdatabase.Songlist;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.r0adkll.slidr.Slidr;

import java.util.ArrayList;
import java.util.Objects;

public class PlaylistActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        mRecycleView = findViewById(R.id.recyclerview_playlist);
        fButton = findViewById(R.id.floating_button_playlist);

        // floating button, start activity addplaylist
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaylistActivity.this, AddPlaylist.class);
                startActivity(intent);
                finish();
            }
        });

        // database builder
        PlaylistDatabase pdb = PlaylistDatabase.getInstance(getApplicationContext());

        // list for database songs
        ArrayList<Playlist> listPlaylist = (ArrayList<Playlist>) pdb.playlistDao().getAllPlaylists();

        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);
        mAdapter = new PlaylistAdapter(listPlaylist,pdb,getApplicationContext());
        mRecycleView.setAdapter(mAdapter);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Playlists");
        int count = mAdapter.getItemCount();
        String subtitle = count +" lists";
        getSupportActionBar().setSubtitle(subtitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.playlist_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_button_song:
            case R.id.action_button_song2:
                Intent intent = new Intent(PlaylistActivity.this, Songlist.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
