package com.example.metronome;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metronome.model.Model;

import java.util.ArrayList;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        private TextView songName;
        public TextView bpm;

        private SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_name);
            bpm = itemView.findViewById(R.id.bpm);
        }
    }

    private ArrayList<Song> mSong;

    public SongAdapter(ArrayList<Song> song) {
        mSong = song;
    }


    // methods
    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        SongViewHolder svh = new SongViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song curItem = mSong.get(position);

        holder.songName.setText(curItem.getSongName());
        holder.bpm.setText(curItem.getBpm());
    }

    @Override
    public int getItemCount() {
        return mSong.size();
    }

}
