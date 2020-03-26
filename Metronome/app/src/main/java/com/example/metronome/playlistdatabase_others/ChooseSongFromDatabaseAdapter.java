package com.example.metronome.playlistdatabase_others;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metronome.R;
import com.example.metronome.playlistdatabase.Playlist;
import com.example.metronome.playlistdatabase.PlaylistDatabase;
import com.example.metronome.songdatabase.Song;
import com.example.metronome.songdatabase.SongDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChooseSongFromDatabaseAdapter extends RecyclerView.Adapter<ChooseSongFromDatabaseAdapter.SongDatabaseViewHolder> {

    public static class SongDatabaseViewHolder extends RecyclerView.ViewHolder{
        public TextView songName;
        public TextView songBpm;
        public CardView songItem;

        public SongDatabaseViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_name_database);
            songBpm = itemView.findViewById(R.id.bpm_database);
            songItem = itemView.findViewById(R.id.cardview_song_database);
        }
    }

    private ArrayList<Song> mSong;
    private List<Song> pSong;
    private PlaylistDatabase pdb;
    private String playlistName;
    private Context context;
    public Playlist playlistUpdated;

    public ChooseSongFromDatabaseAdapter(ArrayList<Song> song, List<Song> listSongs, Playlist myPlaylist, PlaylistDatabase pdb, String name, Context applicationContext)
    { mSong = song; pSong = listSongs; this.pdb = pdb; playlistName = name; context = applicationContext; playlistUpdated = myPlaylist; }


    @NonNull
    @Override
    public SongDatabaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_database_item, parent, false);
        SongDatabaseViewHolder sdvh = new SongDatabaseViewHolder(v);
        return sdvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final SongDatabaseViewHolder holder, int position) {
        Song curItem = mSong.get(position);

        holder.songName.setText(curItem.getSongName());
        holder.songBpm.setText(curItem.getBpm());

        // click on item will add it to current playlist and delete from recyclerview
        holder.songItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pSong.add(new Song(mSong.get(holder.getAdapterPosition()).getBpm(),mSong.get(holder.getAdapterPosition()).getSongName()));
                playlistUpdated.setPlaylist(playlistName);
                playlistUpdated.setPlaylistSongs(pSong);
                pdb.playlistDao().update(playlistUpdated);
                mSong.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                Toast.makeText(context, "Song added", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() { return mSong.size(); }
}
