package com.example.metronome.playlistdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.metronome.R;
import com.example.metronome.songdatabase.AddSong;
import com.example.metronome.songdatabase.Song;
import com.example.metronome.songdatabase.SongDatabase;
import com.example.metronome.songdatabase.Songlist;

import java.util.ArrayList;
import java.util.Objects;

public class AddPlaylist extends AppCompatActivity {
    private Button saveButton;
    private EditText playlistName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_playlist);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add playlist");

        saveButton = findViewById(R.id.button_save_playlist);
        playlistName = findViewById(R.id.playlist_name_add);

        // database builder
        final PlaylistDatabase db = PlaylistDatabase.getInstance(getApplicationContext());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sameName(db, playlistName.getText().toString())){ Toast.makeText(getApplicationContext(), "Playlist already exists", Toast.LENGTH_SHORT).show(); }

                else {
                    db.playlistDao().insert(new Playlist(playlistName.getText().toString()));
                    Intent intent = new Intent(AddPlaylist.this, PlaylistActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    // is there playlist with same name in database?
    public boolean sameName(PlaylistDatabase db, String curName){
        ArrayList<Playlist> listPlaylist = new ArrayList<>();
        listPlaylist = (ArrayList<Playlist>) db.playlistDao().getAllPlaylists();

        int n = 0;
        while(n < listPlaylist.size()){
            if(Objects.equals(listPlaylist.get(n).getPlaylist(),curName)){
                return true;
            }
            n++;
        }
        return false;
    }
}
