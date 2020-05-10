package com.example.metronome.viewModel;

import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.widget.EditText;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.metronome.R;
import com.example.metronome.RunPlayer;

import java.util.Timer;

public class PlayerViewModel {

    private final Fragment fragActivity;
    private SoundPool soundPool;
    private AudioAttributes audioAttributes;
    private boolean checked = false;
    private Timer timer;
    private RunPlayer runPlayer;
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
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            this.soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else {
            this.soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        // set current sound from settings
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(fragActivity.getContext());
        String currentSound = pref.getString("sound", "");
        setSound(currentSound);
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

        timer = new Timer();

        runPlayer = new RunPlayer(soundPool, mSound);

        timer.schedule(runPlayer,0L, 60000/Integer.parseInt(tempo.getText().toString()));

        //soundPool.play(mSound, 1,1,0,-1, 1);

    }

    public void textChangeBpm(CharSequence sequence){
        //this.label = this.fragActivity.getActivity().findViewById(R.id.label);
        //this.label.setSelection(sequence.length());

        if(this.checked){
            timer.cancel();
            timer.purge();

            timer = new Timer();

            runPlayer = new RunPlayer(soundPool, mSound);

            runPlayer.firstBeat = false;

            timer.schedule(runPlayer, 0L, 60000/Integer.parseInt(tempo.getText().toString()));
        }
    }

    public void setSound(String key) {

        if(key.equals("Stick")){
            mSound = soundPool.load(fragActivity.getActivity(),R.raw.stick,1);
        }

        else if(key.equals("Meow")){
            mSound = soundPool.load(fragActivity.getActivity(),R.raw.meow,1);
        }

        else if(key.equals("Beep")){
            mSound = soundPool.load(fragActivity.getActivity(),R.raw.beep4,1);
        }

        else if(key.equals("Drum")){
            mSound = soundPool.load(fragActivity.getActivity(),R.raw.drum1,1);
        }

        else if(key.equals("Bell")){
            mSound = soundPool.load(fragActivity.getActivity(),R.raw.bell2,1);
        }

    }
}
