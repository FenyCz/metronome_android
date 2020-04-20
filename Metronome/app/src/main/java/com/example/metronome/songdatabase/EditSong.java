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

public class EditSong extends AppCompatActivity {

    private EditText editName;
    private EditText editBpm;
    private String curBpm;
    private String curName;
    private Song mySong;
    private SongDatabase db;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_song);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Edit song");

        Button editButton = findViewById(R.id.button_edit);
        editName = findViewById(R.id.song_name_edit);
        editBpm = findViewById(R.id.tempo_edit);

        // get bpm from previous songlist activity
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            curBpm = bundle.getString("bpm");
            curName = bundle.getString("name");
        }
        editBpm.setText(curBpm);
        editName.setText(curName);

        // get instance of database
        db = SongDatabase.getInstance(getApplicationContext());

        ArrayList<Song> list = (ArrayList<Song>) db.songDao().getAllSongs();

        // find current index of song in the list
        int n = 0;
        while(n < list.size()){
            if(Objects.equals(list.get(n).getSongName(),curName)){
                mySong = list.get(n);
                break;
            }
            n++;
        }

        // save song and put it into database
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putIntoPlaylist();
            }
        });
    }

    private void putIntoPlaylist() {
        int bpmInt = 0;
        if(editBpm.getText().toString().isEmpty()){ Toast.makeText(getApplicationContext(), "Tempo (BPM) cannot be null", Toast.LENGTH_SHORT).show(); }

        else {
            double bpmDou = Double.parseDouble(editBpm.getText().toString());
            bpmInt = (int) Math.round(bpmDou);
        }

        if(editName.getText().toString().isEmpty()){ Toast.makeText(getApplicationContext(), "Song name cannot be null", Toast.LENGTH_SHORT).show(); }

        else if(bpmInt < 1){ editBpm.setText("1"); }

        else if(bpmInt > 300){ editBpm.setText("300"); }

        else{
            editBpm.setText(Integer.toString(bpmInt));
            mySong.setBpm(editBpm.getText().toString());
            mySong.setSongName(editName.getText().toString());
            db.songDao().update(mySong);
            Toast.makeText(getApplicationContext(), "Song edited", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(EditSong.this, Songlist.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        putIntoPlaylist();
    }

}