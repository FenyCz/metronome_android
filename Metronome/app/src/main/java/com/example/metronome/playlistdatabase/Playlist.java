package com.example.metronome.playlistdatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.metronome.songdatabase.Song;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "playlists")
public class Playlist {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int id;

    @ColumnInfo(name = "playlist_name")
    public String playlist;

    @ColumnInfo(name = "playlist_songs")
    @TypeConverters(ConvertList.class)
    public List<Song> playlistSongs;

    public Playlist(String playlist){
        this.playlist = playlist;
        this.playlistSongs = new ArrayList<Song>();
    }

    public String getPlaylist() {
        return this.playlist;
    }

    public List<Song> getPlaylistSongs() {
        return this.playlistSongs;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    public void setPlaylistSongs(List<Song> playlistSongs) {
        this.playlistSongs = playlistSongs;
    }
}
