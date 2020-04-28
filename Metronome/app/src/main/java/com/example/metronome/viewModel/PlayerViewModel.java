package com.example.metronome.viewModel;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.widget.EditText;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;

import com.example.metronome.R;
import com.example.metronome.RunMetronome;

import java.util.Timer;

public class PlayerViewModel {

    private final Fragment fragActivity;
    private SoundPool soundPool;
    private AudioAttributes audioAttributes;
    private boolean checked = false;
    private Timer timer;
    private RunMetronome runMetronome = null;
    private ToggleButton tb;
    private EditText tempo;
    private int mSound;

    public PlayerViewModel(Fragment activity) throws IllegalAccessException {
        this.fragActivity = activity;

        // toggle button changer
        this.tb = fragActivity.getActivity().findViewById(R.id.start_button);
        tempo = fragActivity.getActivity().findViewById(R.id.playlist_tempo);

        //set sound of metronome
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            this.audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            this.soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else {
            this.soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        mSound = soundPool.load(fragActivity.getActivity(),R.raw.wood_2,1);
    }

    public void playButtonClick(){
        if(!checked){
            checked = true;
            playSound();
        } else {
            checked = false;
            stopSound();
        }
    }

    public void stopSound() {
        //soundPool.release();
        //soundPool = null;
        this.timer.cancel();
        this.timer.purge();
    }

    public void stopMetronome(){
        if(checked) {
            checked = false;
            stopSound();

            //playScreenOff = true;

            tb.setChecked(false);
        }

    }

    public void playSound(){

        this.timer = new Timer();

        this.runMetronome = new RunMetronome(soundPool, mSound);

        this.timer.schedule(this.runMetronome,0L, 60000/Integer.parseInt(tempo.getText().toString()));

        //soundPool.play(mSound, 1,1,0,-1, 1);

    }

    public void textChangeBpm(CharSequence sequence){
        //this.label = this.fragActivity.getActivity().findViewById(R.id.label);
        //this.label.setSelection(sequence.length());

        if(this.checked){
            timer.cancel();
            timer.purge();

            this.timer = new Timer();

            this.runMetronome = new RunMetronome(soundPool, mSound);

            runMetronome.firstBeat = false;

            this.timer.schedule(this.runMetronome, 0L, 60000/Integer.parseInt(tempo.getText().toString()));
        }
    }
}