package com.example.metronome.songdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Song.class}, version = 1, exportSchema = false)
public abstract class SongDatabase extends RoomDatabase {

    public abstract SongDao songDao();

    private static SongDatabase instance;

    public static synchronized  SongDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), SongDatabase.class, "song_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
