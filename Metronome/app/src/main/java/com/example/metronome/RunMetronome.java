package com.example.metronome;

import android.media.SoundPool;

import java.util.TimerTask;

public class RunMetronome extends TimerTask {

    private int mSound;
    private SoundPool soundPool;
    public boolean firstBeat = false;

    public RunMetronome(SoundPool soundPool, int mSound){
        this.soundPool = soundPool;
        this.mSound = mSound;
    }

    @Override
    public void run() {
        soundPool.play(mSound, 1,1,0,0, 1);
    }
}
