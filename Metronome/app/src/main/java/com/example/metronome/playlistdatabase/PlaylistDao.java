package com.example.metronome.playlistdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.metronome.songdatabase.Song;

import java.util.List;

@Dao
public interface PlaylistDao {
    @Query("SELECT * FROM playlists ORDER BY playlist_name ASC")
    List<Playlist> getAllPlaylists();

    @Update
    void update(Playlist playlist);

    @Insert
    void insert(Playlist playlist);

    @Delete
    void delete(Playlist playlist);
}
