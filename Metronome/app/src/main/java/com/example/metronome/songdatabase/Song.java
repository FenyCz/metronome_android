package com.example.metronome.songdatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "songs")
public class Song {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "tempo")
    private String bpm;

    @ColumnInfo(name = "song_name")
    private String songName = "";

    public Song(String bpm, String songName){
        this.bpm = bpm;
        this.songName = songName;
    }

    public String getSongName() {
        return songName;
    }

    public String getBpm() {
        return bpm;
    }

    public void setBpm(String bpm) { this.bpm = bpm; }

    public void setSongName(String songName) { this.songName = songName; }
}
