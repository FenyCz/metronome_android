package com.example.metronome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.example.metronome.model.Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Playlist extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        mRecycleView = findViewById(R.id.recyclerview_list);

        /*SongDatabase db = Room.databaseBuilder(getApplicationContext(), SongDatabase.class, "song_database")
                //.fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();*/


        ArrayList<Song> list = new ArrayList<Song>();

        //db.songDao().insert(new Song(150, "firstsong"));
        list.add(new Song("149", "Gogo"));
        list.add(new Song("149", "Song 2"));
        list.add(new Song("149", "Song 2"));
        list.add(new Song("149", "Song 2"));
        list.add(new Song("149", "Song 2"));
        list.add(new Song("149", "Song 2"));
        list.add(new Song("149", "Song 2"));
        list.add(new Song("149", "Song 2"));
        list.add(new Song("149", "Song 2"));

        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);

        // nastavení čáreček mezi jednotlivými itemy v playlistu
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecycleView.getContext(), DividerItemDecoration.VERTICAL);
        mRecycleView.addItemDecoration(dividerItemDecoration);

        mAdapter = new SongAdapter(list);
        mRecycleView.setAdapter(mAdapter);
    }
}
