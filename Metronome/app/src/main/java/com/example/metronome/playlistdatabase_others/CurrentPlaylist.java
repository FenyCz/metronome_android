package com.example.metronome.playlistdatabase_others;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metronome.R;
import com.example.metronome.playlistdatabase.Playlist;
import com.example.metronome.playlistdatabase.PlaylistDatabase;
import com.example.metronome.songdatabase.Song;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CurrentPlaylist extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String curName;
    private TextView playlistName;
    private FloatingActionButton fButton;
    private Integer curNumber;
    private String songName;
    private Integer songBpm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_playlist);

        mRecycleView = findViewById(R.id.recyclerview_playlist_detail);
        playlistName = findViewById(R.id.playlist_detail);
        fButton = findViewById(R.id.floating_button_playlist_detail);

        // get playlist name from previous activity
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            curName = bundle.getString("name");
            songName = bundle.getString("song_name");
            songBpm = bundle.getInt("bpm");
        }

        // floating button, start activity choosedatabasesong
        fButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CurrentPlaylist.this, ChooseSongFromDatabase.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name", curName);
                startActivity(intent);
                finish();
            }
        });

        // database builder
        final PlaylistDatabase pdb = PlaylistDatabase.getInstance(getApplicationContext());

        ArrayList<Playlist> listPlaylist = (ArrayList<Playlist>) pdb.playlistDao().getAllPlaylists();

        // find current index of playlist in the list
        int n = 0;
        while(n < listPlaylist.size()){
            if(Objects.equals(listPlaylist.get(n).getPlaylist(),curName)){
                curNumber = n;
                break;
            }
            n++;
        }

        // list of songs of current playlist
        final List<Song> listSongs = listPlaylist.get(curNumber).getPlaylistSongs();
        final Playlist myPlaylist = listPlaylist.get(curNumber);

        // recycler view settings
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);
        mAdapter = new CurrentPlaylistAdapter(listSongs, myPlaylist, pdb,getApplicationContext());
        mRecycleView.setAdapter(mAdapter);

        // allow to change position of items in recyclerview
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {
                int position_dragged = dragged.getAdapterPosition();
                int position_target = target.getAdapterPosition();

                Collections.swap(listSongs, position_dragged, position_target);
                mAdapter.notifyItemMoved(position_dragged,position_target);
                pdb.playlistDao().update(myPlaylist);

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        helper.attachToRecyclerView(mRecycleView);

        // set title and subtitle of activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Playlist");
        playlistName.setText(curName);
    }
}
