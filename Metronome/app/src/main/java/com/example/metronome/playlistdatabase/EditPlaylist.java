package com.example.metronome.playlistdatabase;

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

public class EditPlaylist extends AppCompatActivity {
    private EditText editName;
    private Button editButton;
    private String curName;
    private PlaylistDatabase db;
    private Playlist myPlaylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_playlist);
        getSupportActionBar().setTitle("Edit playlist");

        editName = findViewById(R.id.song_name_edit);
        editButton = findViewById(R.id.button_edit);

        // get playlist name from previous activity
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            curName = bundle.getString("name");
        }
        editName.setText(curName);

        // database build
        db = PlaylistDatabase.getInstance(getApplicationContext());

        ArrayList<Playlist> listPlaylist = (ArrayList<Playlist>) db.playlistDao().getAllPlaylists();

        // find current index of playlist in the list
        int n = 0;
        while(n < listPlaylist.size()){
            if(Objects.equals(listPlaylist.get(n).getPlaylist(),curName)){
                myPlaylist = listPlaylist.get(n);
                break;
            }
            n++;
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPlaylist.setPlaylist(editName.getText().toString());
                db.playlistDao().update(myPlaylist);
                Toast.makeText(getApplicationContext(), "Playlist edited", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditPlaylist.this, PlaylistActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        myPlaylist.setPlaylist(editName.getText().toString());
        db.playlistDao().update(myPlaylist);
        Toast.makeText(getApplicationContext(), "Playlist edited", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EditPlaylist.this, PlaylistActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
