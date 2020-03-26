package com.example.metronome.songdatabase;

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
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.metronome.R;
import com.example.metronome.playlistdatabase.AddPlaylist;
import com.example.metronome.playlistdatabase.PlaylistActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class Songlist extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton fButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songlist);

        mRecycleView = findViewById(R.id.recyclerview_list);
        fButton = findViewById(R.id.floating_button);

        // floating button, start activity addsong
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Songlist.this, AddSong.class);
                intent.putExtra("bpm", 120);
                startActivity(intent);
                finish();
            }
        });

        // get instance of database
        SongDatabase db = SongDatabase.getInstance(getApplicationContext());

        // list for database songs
        ArrayList<Song> list = (ArrayList<Song>) db.songDao().getAllSongs();

        // recyclerview settings
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);

        mAdapter = new SongAdapter(list,db,getApplicationContext());
        mRecycleView.setAdapter(mAdapter);

        // set title and subtitle of activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Songs");
        String counSong = mAdapter.getItemCount() + " songs";
        getSupportActionBar().setSubtitle(counSong);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.songlist_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_button_playlist:
            case R.id.action_button_playlist2:
                Intent intent = new Intent(Songlist.this, PlaylistActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
