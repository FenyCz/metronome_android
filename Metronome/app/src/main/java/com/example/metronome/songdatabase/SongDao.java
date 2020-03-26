package com.example.metronome.songdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.metronome.songdatabase.Song;

import java.util.List;

@Dao
public interface SongDao {
    @Query("SELECT * FROM songs ORDER BY song_name ASC")
    List<Song> getAllSongs();

    @Insert
    void insert(Song song);

    @Update
    void update(Song song);

    @Delete
    void delete(Song song);

}
