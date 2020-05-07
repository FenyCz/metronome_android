package com.example.metronome;

import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.Build;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.metronome.viewModel.MetronomeViewModel;

import java.util.TimerTask;

public class RunMetronome extends TimerTask {

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RunMetronome(SoundPool soundPool, int mSound, ViewGroup accentsViewGroup, Fragment fragActivity, int accentsNumber, Boolean increase, String increaseBar, String increaseBpm, MetronomeViewModel viewModel){
        this.soundPool = soundPool;
        this.mSound = mSound;
        accents = accentsViewGroup;
        activity = fragActivity;
        this.accentsNumber = accentsNumber - 1;
        this.increase = increase;
        this.increaseBar = increaseBar;
        this.increaseBpm = increaseBpm;
        mViewmodel = viewModel;

        red = activity.getActivity().getDrawable(R.drawable.circle_accent);
        orange = activity.getActivity().getDrawable(R.drawable.circle_accent_default);
        bar = activity.getActivity().findViewById(R.id.text_view_bar);
    }

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
            activity.getActivity().runOnUiThread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void run() {

                    soundPool.play(mSound, 1,1,0,0, 1);

                    // only one accent, loop with it
                    if(accentsNumber == 0){
                        accents.getChildAt(0).setForeground(red);
                        cBar = 1;
                        rBar++;
                    }

                    // first beat
                    else if (round == 0)
                    {
                        accents.getChildAt(accentsNumber).setForeground(orange);
                        accents.getChildAt(0).setForeground(red);
                        round++;
                        cBar = round;
                        rBar += 1;
                    }

                    // other beats
                    else if (round != accentsNumber)
                    {
                        accents.getChildAt(round - 1).setForeground(orange);
                        accents.getChildAt(round).setForeground(red);
                        round++;
                        cBar = round;
                    }

                    // last one beat
                    else if (round == accentsNumber)
                    {
                        accents.getChildAt(round - 1).setForeground(orange);
                        accents.getChildAt(round).setForeground(red);
                        cBar = round + 1;
                        round = 0;

                        iBar++;
                    }

                    if (increase) {

                        if (iBar == Integer.parseInt(increaseBar)) {
                            mViewmodel.buttonChangeBpm(Integer.parseInt(increaseBpm));
                            iBar = 0;
                        }
                    }
                    // set text of current beat
                    bar.setText(String.valueOf(rBar) + "." + String.valueOf(cBar));
                }
            });
        }
    }
}
