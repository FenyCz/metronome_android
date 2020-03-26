package com.example.metronome.playlistdatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metronome.MainActivity;
import com.example.metronome.R;
import com.example.metronome.playlistdatabase_others.CurrentPlaylist;

import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

    public static class PlaylistViewHolder extends RecyclerView.ViewHolder {
        public TextView playlistName;
        public ImageView popUpMenu;
        public CardView playlistItem;
        public TextView playerName;

        public PlaylistViewHolder(@NonNull View itemView) {
            super(itemView);
            playlistName = itemView.findViewById(R.id.playlist_name);
            popUpMenu = itemView.findViewById(R.id.popup);
            playlistItem = itemView.findViewById(R.id.cardview_playlist);
        }
    }

    private ArrayList<Playlist> mPlaylist;
    private final PlaylistDatabase db;
    private final Context mContext;

    public PlaylistAdapter(ArrayList<Playlist> playList, PlaylistDatabase pdb, Context context){ this.mPlaylist = playList; this.db = pdb; this.mContext = context; }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
        PlaylistViewHolder pvh = new PlaylistViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaylistViewHolder holder, final int position) {
        final Playlist curItem = mPlaylist.get(position);

        holder.playlistName.setText(curItem.getPlaylist());
        holder.popUpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(mContext,v);
                popup.inflate(R.menu.popup_menu_songlist);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                editItem(holder,v,position);
                                return true;
                            case R.id.delete:
                                Toast.makeText(mContext, "Playlist deleted", Toast.LENGTH_SHORT).show();
                                deleteItem(holder,v);
                                return true;
                            case R.id.load_in:
                                Intent intent = new Intent(v.getContext(), MainActivity.class);
                                intent.putExtra("playlist_name", mPlaylist.get(holder.getAdapterPosition()).getPlaylist());
                                intent.putExtra("bpm", 100);
                                intent.putExtra("tab", 1);
                                mContext.startActivity(intent);
                                return true;
                            default:
                                return true;
                        }
                    }
                });
                popup.show();
            }
        });
        // click on card will open current playlist
        holder.playlistItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CurrentPlaylist.class);
                intent.putExtra("name", mPlaylist.get(holder.getAdapterPosition()).getPlaylist());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPlaylist.size();
    }

    // delete item from database and recyclerview
    public void deleteItem(PlaylistViewHolder holder, View v){
        db.playlistDao().delete(mPlaylist.get(holder.getAdapterPosition()));
        mPlaylist.remove(holder.getAdapterPosition());
        notifyItemRemoved(holder.getAdapterPosition());
        Intent intent = new Intent(v.getContext(), PlaylistActivity.class);
        v.getContext().startActivity(intent);
        ((PlaylistActivity)v.getContext()).finish();
    }

    // edit item in database
    private void editItem(PlaylistViewHolder holder, View v, int position) {
        Intent intent = new Intent(v.getContext(), EditPlaylist.class);
        intent.putExtra("name", mPlaylist.get(holder.getAdapterPosition()).getPlaylist());
        mPlaylist.remove(holder.getAdapterPosition());
        v.getContext().startActivity(intent);
    }
}
