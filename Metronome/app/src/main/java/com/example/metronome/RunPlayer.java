package com.example.metronome;

import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.metronome.viewModel.MetronomeViewModel;

import java.util.TimerTask;

public class RunPlayer extends TimerTask {

    private MetronomeViewModel mViewmodel;
    private Boolean increase;
    private String increaseBar;
    private String increaseBpm;
    private int mSound;
    private SoundPool soundPool;
    public boolean firstBeat = false;
    private ViewGroup accents;
    private Fragment activity;
    private Drawable red;
    private Drawable orange;
    private int accentsNumber;
    private int round = 0;
    private int cBar = 0;
    private int rBar = -1;
    private int iBar = 0;
    private TextView bar;

    public RunPlayer(SoundPool soundPool, int mSound){
        this.soundPool = soundPool;
        this.mSound = mSound;
    }

    @Override
    public void run() {

        // first time beat metronome, prevent lag
        if(!firstBeat){
            soundPool.play(mSound, 0,0,0,0, 1);
            firstBeat=true;
        }

        else
        {
            soundPool.play(mSound, 1,1,0,0, 1);
        }
    }
}
