package com.example.metronome.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.metronome.*;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

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

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.action_save:
                Intent intent = new Intent(getActivity(), AddSong.class);
                intent.putExtra("bpm", mData.getBpm());
                this.startActivity(intent);
                return true;
            case R.id.action_songlist:
                Intent pIntent = new Intent(getActivity(), Songlist.class);
                this.startActivity(pIntent);
            case R.id.action_bluetooth:
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

        // set default tempo or tempo of song
        mData.setBpm(((MainActivity)getActivity()).getResult());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        try {
            mViewModel = new MetronomeViewModel(this, mData);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //set databinding to MetronomeViewModel
        fragmentMetronomeBinding.setMViewModel(mViewModel);
    }
}
