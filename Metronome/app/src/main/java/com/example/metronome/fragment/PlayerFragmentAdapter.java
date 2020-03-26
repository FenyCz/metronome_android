package com.example.metronome.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.metronome.R;
import com.example.metronome.playlistdatabase.PlaylistAdapter;
import com.example.metronome.songdatabase.Song;

import java.util.ArrayList;
import java.util.List;

public class PlayerFragmentAdapter extends RecyclerView.Adapter<PlayerFragmentAdapter.PlayerFragmentViewHolder> {

    public static class PlayerFragmentViewHolder extends RecyclerView.ViewHolder{
        private TextView songName;
        public TextView bpm;
        public CardView songItem;

        public PlayerFragmentViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_name_player);
            bpm = itemView.findViewById(R.id.bpm_player);
            songItem = itemView.findViewById(R.id.cardview_player);
        }
    }

    private List<Song> mSong;
    private Context mContext;
    private Integer index = 0;

    public PlayerFragmentAdapter(List<Song> songs, Context context){ mSong = songs; mContext = context; }

    @NonNull
    @Override
    public PlayerFragmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item, parent, false);
        PlayerFragmentViewHolder pfvh = new PlayerFragmentViewHolder(v);
        return pfvh;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerFragmentViewHolder holder, final int position) {
        final Song curItem = mSong.get(position);

        holder.songName.setText(curItem.getSongName());
        holder.bpm.setText(curItem.getBpm());

        // click on item will set bpm and change color of focused song
        holder.songItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = position;
                notifyDataSetChanged();
            }
        });

        // choosing item will change color
        if(index == position){
            holder.songItem.setCardBackgroundColor(mContext.getResources().getColor(R.color.secondaryColor));
            holder.songName.setTextColor(Color.parseColor("#eeeeee"));
            holder.bpm.setTextColor(Color.parseColor("#eeeeee"));
            Intent intent = new Intent("song_data");
            intent.putExtra("bpm", mSong.get(position).getBpm());
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        }

        // others wont
        else {
            holder.songItem.setCardBackgroundColor(Color.parseColor("#eeeeee"));
            holder.songName.setTextColor(Color.parseColor("#000000"));
            holder.bpm.setTextColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public int getItemCount() { return mSong.size();}
}
