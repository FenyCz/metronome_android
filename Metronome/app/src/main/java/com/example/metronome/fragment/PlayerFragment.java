package com.example.metronome.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.metronome.MainActivity;
import com.example.metronome.R;
import com.example.metronome.playlistdatabase.Playlist;
import com.example.metronome.playlistdatabase.PlaylistActivity;
import com.example.metronome.playlistdatabase.PlaylistDatabase;
import com.example.metronome.songdatabase.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class PlayerFragment extends Fragment {
    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView playerName;
    private Integer curNumber;
    private TextView playlistTempo;
    private boolean playlistFound = false;

    public PlayerFragment() {
        // Required empty public constructor
    }

    public static PlayerFragment newInstance() {
        return new PlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_save);
        item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                return true;
            case R.id.action_bluetooth:
                return true;
            case R.id.action_songlist:
                Intent pIntent = new Intent(getActivity(), PlaylistActivity.class);
                this.startActivity(pIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_player, container, false);
        mRecycleView = view.findViewById(R.id.recyclerview_player);
        playerName = view.findViewById(R.id.player_name);
        playlistTempo = view.findViewById(R.id.playlist_tempo);

        playerName.setText(((MainActivity) Objects.requireNonNull(getActivity())).getPlaylistName());

        // receive tempo from recycleview adapter to fragment - call function mReceiver
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).registerReceiver(mReceiver, new IntentFilter("song_data"));

        // database builder
        PlaylistDatabase pdb = PlaylistDatabase.getInstance(getContext());

        // list for database songs
        ArrayList<Playlist> listPlaylist = (ArrayList<Playlist>) pdb.playlistDao().getAllPlaylists();

        // fubd
        int n = 0;
        while(n < listPlaylist.size()){
            if(Objects.equals(listPlaylist.get(n).getPlaylist(),playerName.getText())){
                curNumber = n;
                playlistFound = true;
                break;
            }
            n++;
        }

        if(playlistFound) {
            // list of songs of current playlist
            List<Song> listSongs = listPlaylist.get(curNumber).getPlaylistSongs();
            setRecycleView(view, listSongs, 0);
        }

        // hodim tam prvni playlist, ktery je v seznamu
        else if(listPlaylist.size() != 0){
            List<Song> listSongs = listPlaylist.get(0).getPlaylistSongs();
            playerName.setText(listPlaylist.get(0).getPlaylist());
            setRecycleView(view, listSongs, 0);
        }

        // pokud v seznamu zadny playlist neni
        else { playerName.setText("No playlist"); }

        return view;
    }

    // function for receive the broadcast
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            playlistTempo.setText(intent.getStringExtra("bpm"));
            //Toast.makeText(getActivity(), playlistTempo.getText(), Toast.LENGTH_SHORT).show();
        }
    };

    public void setRecycleView(View view, final List<Song> listSongs, Integer pos){
        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecycleView.setLayoutManager(mLayoutManager);
        mAdapter = new PlayerFragmentAdapter(listSongs, view.getContext());
        mRecycleView.setAdapter(mAdapter);

    }
}
