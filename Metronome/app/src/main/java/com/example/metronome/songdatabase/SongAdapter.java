package com.example.metronome.songdatabase;

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

import java.util.ArrayList;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        private TextView songName;
        public TextView bpm;
        private ImageView popUpMenu;
        private CardView songItem;

        private SongViewHolder(@NonNull final View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_name);
            bpm = itemView.findViewById(R.id.bpm);
            popUpMenu = itemView.findViewById(R.id.popup);
            songItem = itemView.findViewById(R.id.cardview);
        }
    }

    private ArrayList<Song> mSong;
    private SongDatabase db;
    private Context context;

    // constructor
    public SongAdapter(ArrayList<Song> song, SongDatabase dtb, Context applicationContext) { mSong = song; db = dtb; context = applicationContext; }

    // Adapter methods
    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new SongViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final SongViewHolder holder, final int position) {
        Song curItem = mSong.get(position);

        holder.songName.setText(curItem.getSongName());
        holder.bpm.setText(curItem.getBpm());

        // card popup menu
        holder.popUpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(context,v);
                popup.inflate(R.menu.popup_menu_songlist);
                MenuItem item = popup.getMenu().findItem(R.id.load_in);
                item.setVisible(false);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                editItem(holder,v,position);
                                return true;
                            case R.id.delete:
                                Toast.makeText(context, "Song deleted", Toast.LENGTH_SHORT).show();
                                deleteItem(holder,v);
                                notifyItemRangeChanged(holder.getAdapterPosition(), getItemCount());
                                return true;
                            default:
                                return true;
                        }
                    }
                });
                popup.show();
            }
        });

        // click on card will set current tempo to metronome and open main activity
        holder.songItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("bpm", Integer.parseInt(mSong.get(holder.getAdapterPosition()).getBpm()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSong.size();
    }

    // delete item from database and recyclerview
    public void deleteItem(SongViewHolder holder, View v){
        db.songDao().delete(mSong.get(holder.getAdapterPosition()));
        mSong.remove(holder.getAdapterPosition());
        notifyItemRemoved(holder.getAdapterPosition());
        Intent intent = new Intent(v.getContext(), Songlist.class);
        v.getContext().startActivity(intent);
        ((Songlist)v.getContext()).finish();
    }

    // start new activity where you update song
    private void editItem(SongViewHolder holder, View v, int position) {
        Intent intent = new Intent(v.getContext(), EditSong.class);
        intent.putExtra("bpm", mSong.get(holder.getAdapterPosition()).getBpm());
        intent.putExtra("name", mSong.get(holder.getAdapterPosition()).getSongName());
        mSong.remove(holder.getAdapterPosition());
        v.getContext().startActivity(intent);
    }

}
