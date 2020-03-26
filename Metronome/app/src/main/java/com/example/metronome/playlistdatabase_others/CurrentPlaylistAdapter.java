package com.example.metronome.playlistdatabase_others;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metronome.R;
import com.example.metronome.playlistdatabase.Playlist;
import com.example.metronome.playlistdatabase.PlaylistDatabase;
import com.example.metronome.songdatabase.Song;

import java.util.List;

public class CurrentPlaylistAdapter extends RecyclerView.Adapter<CurrentPlaylistAdapter.CurrentPlaylistViewHolder> {

    public static class CurrentPlaylistViewHolder extends RecyclerView.ViewHolder {
        private TextView songName;
        public TextView bpm;
        public ImageView deleteItem;
        public CardView songItem;

        public CurrentPlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_name_cur);
            bpm = itemView.findViewById(R.id.bpm_cur);
            deleteItem = itemView.findViewById(R.id.trash);
            songItem = itemView.findViewById(R.id.cardview_cur);
        }
    }

    List<Song> mSong;
    PlaylistDatabase pdb;
    Context applicationContext;
    Playlist updatedPlaylist;

    public CurrentPlaylistAdapter(List<Song> listSongs, Playlist myPlaylist, PlaylistDatabase pdb, Context applicationContext) { this.mSong = listSongs; this.updatedPlaylist = myPlaylist; this.pdb = pdb; this.applicationContext = applicationContext;}

    @NonNull
    @Override
    public CurrentPlaylistAdapter.CurrentPlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.c_song_item, parent, false);
        return new CurrentPlaylistViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CurrentPlaylistAdapter.CurrentPlaylistViewHolder holder, int position) {
        Song curItem = mSong.get(position);

        holder.songName.setText(curItem.getSongName());
        holder.bpm.setText(curItem.getBpm());

        // delete item from current playlist and recyclerview
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSong.remove(holder.getAdapterPosition());
                updatedPlaylist.setPlaylistSongs(mSong);
                pdb.playlistDao().update(updatedPlaylist);
                notifyItemRemoved(holder.getAdapterPosition());
                Toast.makeText(v.getContext(), "Song removed from playlist", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() { return mSong.size(); }
}
