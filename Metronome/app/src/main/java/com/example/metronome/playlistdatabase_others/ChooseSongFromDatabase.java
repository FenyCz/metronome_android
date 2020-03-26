package com.example.metronome.playlistdatabase_others;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.metronome.R;
import com.example.metronome.playlistdatabase.Playlist;
import com.example.metronome.playlistdatabase.PlaylistDatabase;
import com.example.metronome.songdatabase.Song;
import com.example.metronome.songdatabase.SongDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChooseSongFromDatabase extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String curName;
    private Integer curNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_song_from_database);

        mRecycleView = findViewById(R.id.recyclerview_database);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            curName = bundle.getString("name");
        }

        /////////////////////////////////////// PLAYLIST DATABASE

        // playlist database builder
        PlaylistDatabase pdb = PlaylistDatabase.getInstance(getApplicationContext());

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
        List<Song> listSongs = listPlaylist.get(curNumber).getPlaylistSongs();
        Playlist myPlaylist = listPlaylist.get(curNumber);

        /////////////////////////////////////////// SONG DATABASE

        // song database builder
        SongDatabase db = SongDatabase.getInstance(getApplicationContext());

        // list for database songs
        ArrayList<Song> list = (ArrayList<Song>) db.songDao().getAllSongs();

        // dont want to add songs, that are already in database = all songs - minus database songs
        // list - listSongs
        int k = 0;
        while(k < listSongs.size()){
            for(int in = 0; in < list.size(); in++){
                if(Objects.equals(list.get(in).getSongName(),listSongs.get(k).getSongName())){
                    list.remove(in);
                }
            }
            k++;
        }

        // recycler view settings
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLayoutManager);
        mAdapter = new ChooseSongFromDatabaseAdapter(list,listSongs,myPlaylist,pdb,curName,getApplicationContext());
        mRecycleView.setAdapter(mAdapter);

        // set title and subtitle of activity
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Put songs into playlist");
        String counSong = mAdapter.getItemCount() + " songs";
        getSupportActionBar().setSubtitle(counSong);
    }
}
