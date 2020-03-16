package com.example.metronome;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Song.class}, version = 1)
public abstract class SongDatabase extends RoomDatabase {

    public abstract SongDao songDao();

    /*private static SongDatabase instance;

    public static synchronized  SongDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), SongDatabase.class, "song_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(songCallback)
                    .build();
        }
        return instance;
    }

    private static SongDatabase.Callback songCallback = new SongDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{
        private SongDao songDao;

        private PopulateDbAsyncTask(SongDatabase db) {
            songDao = db.songDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            songDao.insert(new Song(150, "Song 2"));
            return null;
        }
    }*/

}
