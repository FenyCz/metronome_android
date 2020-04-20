package com.example.metronome.songdatabase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.metronome.R;

import java.util.ArrayList;
import java.util.Objects;

public class AddSong extends AppCompatActivity {

    private EditText songName;
    private EditText bpm;
    private Integer curBpm;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add song");

        Button saveButton = findViewById(R.id.button_save);
        songName = findViewById(R.id.song_name);
        bpm = findViewById(R.id.tempo);

        // get bpm from previous main activity
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            curBpm = bundle.getInt("bpm");
        }
        bpm.setText(curBpm.toString());

        // get instance of database
        final SongDatabase db = SongDatabase.getInstance(getApplicationContext());

        // save song into database
        saveButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int bpmInt = 0;

                // empty bpm?
                if(bpm.getText().toString().isEmpty()){ Toast.makeText(getApplicationContext(), "Tempo (BPM) cannot be null", Toast.LENGTH_SHORT).show(); }

                else {
                    // double to int - 0.222222 to 0
                    double bpmDou = Double.parseDouble(bpm.getText().toString());
                    bpmInt = (int) Math.round(bpmDou);
                }

                // empty text?
                if(bpm.getText().toString().isEmpty()){ Toast.makeText(getApplicationContext(), "Tempo (BPM) cannot be null", Toast.LENGTH_SHORT).show(); }

                else if(songName.getText().toString().isEmpty()){ Toast.makeText(getApplicationContext(), "Song name cannot be null", Toast.LENGTH_SHORT).show(); }

                else if(bpmInt < 1){ bpm.setText("1"); }

                else if(bpmInt > 300){ bpm.setText("300"); }

                else if(sameName(db,songName.getText().toString())){ Toast.makeText(getApplicationContext(), "Song already exists", Toast.LENGTH_SHORT).show(); }

                else {
                    // create new song in database
                    bpm.setText(Integer.toString(bpmInt));
                    db.songDao().insert(new Song(bpm.getText().toString(), songName.getText().toString()));
                    Intent intent = new Intent(AddSong.this, Songlist.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    // is there song with same name in database?
    public boolean sameName(SongDatabase db, String curName){
        ArrayList<Song> listSongs = (ArrayList<Song>) db.songDao().getAllSongs();

        int n = 0;
        while(n < listSongs.size()){
            if(Objects.equals(listSongs.get(n).getSongName(),curName)){
                return true;
            }
            n++;
        }
        return false;
    }
}
