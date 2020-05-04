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
