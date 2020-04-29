package com.example.metronome.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.metronome.Demo1;
import com.example.metronome.MainActivity;
import com.example.metronome.R;
import com.example.metronome.bluetooth.BluethootActivity;
import com.example.metronome.databinding.FragmentMetronomeBinding;
import com.example.metronome.model.Model;
import com.example.metronome.songdatabase.AddSong;
import com.example.metronome.songdatabase.Songlist;
import com.example.metronome.viewModel.MetronomeViewModel;

public class MetronomeFragment extends Fragment {

    //databinding
    private FragmentMetronomeBinding fragmentMetronomeBinding;

    // Model
    private Model mData = new Model();

    // ViewModel
    private MetronomeViewModel mViewModel;


    public MetronomeFragment() {
        // Required empty public constructor
    }

    public static MetronomeFragment newInstance() {
        MetronomeFragment fragment = new MetronomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //default
        super.onCreate(savedInstanceState);

        //enable options
        setHasOptionsMenu(true);
    }

    //action buttons
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_playlist);
        item.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent sIntent = new Intent(getActivity(), Demo1.class);
                this.startActivity(sIntent);
                return true;
            case R.id.action_save:
                Intent intent = new Intent(getActivity(), AddSong.class);
                intent.putExtra("bpm", mData.getBpm());
                this.startActivity(intent);
                return true;
            case R.id.action_songlist:
                Intent pIntent = new Intent(getActivity(), Songlist.class);
                this.startActivity(pIntent);
                return true;
            case R.id.action_bluetooth:
                Intent bIntent = new Intent(getActivity(), BluethootActivity.class);
                this.startActivity(bIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        //databinding activating
        fragmentMetronomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_metronome,container,false);
        View view = fragmentMetronomeBinding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // set default tempo or tempo of song
        mData.setBpm(((MainActivity)getActivity()).getResult());

        try {
            mViewModel = new MetronomeViewModel(this, mData);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //set databinding to MetronomeViewModel
        fragmentMetronomeBinding.setMViewModel(mViewModel);
    }

    @Override
    public void onPause() {
        super.onPause();

        PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        if (pm.isScreenOn()) {
            mViewModel.stopMetronome();
        }

    }

    /*@Override
    public void onStop() {
        super.onStop();
        PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        if (pm.isScreenOn()) {
            // switch to audio
        }
        else {
            if(mViewModel.playScreenOff){
                //mViewModel.playButtonClick();
                mViewModel.tb.setChecked(true);
                mViewModel.playScreenOff = false;
            }
        }
    }*/
}
