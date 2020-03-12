package com.example.metronome;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.metronome.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.metronome.databinding.FragmentMetronomeBinding;
import com.example.metronome.model.Model;
import com.example.metronome.viewModel.MetronomeViewModel;
import com.sdsmdg.harjot.crollerTest.Croller;
import com.sdsmdg.harjot.crollerTest.OnCrollerChangeListener;

public class Metronome extends Fragment {

    //databinding
    private FragmentMetronomeBinding fragmentMetronomeBinding;

    // Model
    private Model mData;

    // ViewModel
    private MetronomeViewModel mViewModel;


    public Metronome() {
        // Required empty public constructor
    }

    public static Metronome newInstance() {
        Metronome fragment = new Metronome();
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
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        //databinding activating
        fragmentMetronomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_metronome,container,false);
        View view = fragmentMetronomeBinding.getRoot();

        //default 100 bpm
        mData = new Model();
        mData.setBpm(100);

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
