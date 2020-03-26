package com.example.metronome.playlistdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Playlist.class}, version = 3, exportSchema = false)
public abstract class PlaylistDatabase extends RoomDatabase {

    public abstract PlaylistDao playlistDao();

    private static PlaylistDatabase instance;

    public static synchronized PlaylistDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), PlaylistDatabase.class, "playlist_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
