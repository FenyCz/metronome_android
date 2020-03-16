package com.example.metronome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.metronome.model.Model;

public class AddSong extends AppCompatActivity {

    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add song");

        saveButton = findViewById(R.id.button_save);

        /*final SongDatabase db = Room.databaseBuilder(getApplicationContext(), SongDatabase.class, "song_database")
                //.fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();*/

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSong.this, Playlist.class);
                startActivity(intent);
            }
        });
    }
}
