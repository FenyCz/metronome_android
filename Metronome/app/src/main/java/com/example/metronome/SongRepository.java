package com.example.metronome;

import android.app.Application;
import android.os.AsyncTask;

import com.example.metronome.model.Model;

public class SongRepository {
    /*private SongDao mSongDao;


    public SongRepository(Application application) {
        SongDatabase db = SongDatabase.getInstance(application);
        mSongDao = db.songDao();
    }

    public void insert(Song song){ new InsertModelAsyncTask(mSongDao).execute(song); }

    public void update(Song song){
        new UpdateModelAsyncTask(mSongDao).execute(song);
    }

    public void delete(Song song){
        new DeleteModelAsyncTask(mSongDao).execute(song);
    }


    private static class InsertModelAsyncTask extends AsyncTask<Song, Void, Void> {
        private SongDao songDao;

        private InsertModelAsyncTask(SongDao songDao){
            this.songDao = songDao;
        }

        @Override
        protected Void doInBackground(Song... songs) {
            songDao.insert(songs[0]);
            return null;
        }
    }

    private static class UpdateModelAsyncTask extends AsyncTask<Song, Void, Void> {
        private SongDao songDao;

        private UpdateModelAsyncTask(SongDao songDao){
            this.songDao = songDao;
        }

        @Override
        protected Void doInBackground(Song... songs) {
            songDao.update(songs[0]);
            return null;
        }
    }

    private static class DeleteModelAsyncTask extends AsyncTask<Song, Void, Void> {
        private SongDao songDao;

        private DeleteModelAsyncTask(SongDao songDao){
            this.songDao = songDao;
        }

        @Override
        protected Void doInBackground(Song... songs) {
            songDao.delete(songs[0]);
            return null;
        }
    }*/
}
