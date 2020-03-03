package com.example.metronome.viewModel;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.metronome.Metronome;
import com.example.metronome.R;
import com.example.metronome.model.Model;
import com.example.metronome.model.SoundTask;
import com.sdsmdg.harjot.crollerTest.Croller;

import java.util.HashMap;
import java.util.Timer;

public class ViewModel extends androidx.lifecycle.ViewModel{


    private Model data;
    private Fragment fragActivity;
    private SoundPool soundPool;
    private AudioAttributes audioAttributes;
    private int mSound;
    private Timer timer;
    private boolean timerIsRunning = false;

    private Croller croller;

    private GridLayout accentBar;

    private HashMap<String, int[]> beatsSounds = null;

    private SoundTask soundTask = null;

    public ViewModel(Fragment activity, Model model) throws IllegalAccessException {
        this.data = model;
        this.fragActivity = activity;

        //default state of metronome
        //data.setBpm(150);

        setDefault();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            this.audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            this.soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else {
            this.soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        mSound = soundPool.load(activity.getActivity(),R.raw.wood_1,1);

        //TODO : accent bar
        //accentBar = this.activity.findViewById(R.id.accentBar);


        //TODO continue - finish me
        /*beatsSounds = SoundLoader.loadSounds(this.activity, this.soundPool);

        Spinner spinner = this.activity.findViewById(R.id.spinner_beat_sound);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this.activity, android.R.layout.simple_spinner_item,
                        beatsSounds.keySet().toArray(new String[(beatsSounds.size())]));

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setSelection(getIndex(spinner,this.data.getSoundName()));*/
    }

    public void playSound(){
        soundPool.play(mSound, 1,1,0,0, 1);
        soundPool.release();
        soundPool = null;
    }

    public void setDefault() {
        Croller croller = (Croller) this.fragActivity.getActivity().findViewById(R.id.croller);
        //croller.setMax(300); //max number
        //croller.setProgress(data.getBpm()); //default number of seek bar
        //croller.setMin(1);
    }

    public void textChangeCrooler(){
        Croller croller = fragActivity.getActivity().findViewById(R.id.croller);
    }

        // TODO : odkomentovat, az budu chtit pouzivat zmenu klikani
    // stisk tlačítka play, spustí nebo zastaví timer
    /*public void onClickedPlayButton(boolean checked){
        if (checked){
            this.runTimer();
        }
        else {
            this.cancelTimer();
        }
    }

    //při kliku na tlačítko přičte nebo odečte BPM
    public void onClickedPlus( int plus){
        this.data.setBpm(this.data.getBpm() + plus);
    }

    //spustí timer který přehrává soundtask
    private void runTimer() {
        this.timerIsRunning = true;
        this.timer = new Timer();

        if (soundTask == null) {
            Spinner spinner = this.activity.getView().findViewById(R.id.croller);
            String selected = spinner.getSelectedItem().toString();

            soundTask = new SoundTask(soundPool, beatsSounds.get(selected), data.getAccentArr(), data.getCountOfBeats());
            soundTask.register((CurrentBeatDraw) this);
        }

        this.timer.schedule(soundTask, 0L, data.getFreq());
    }

    //zastaví timer
    public void cancelTimer(){
        this.timerIsRunning = false;
        this.timer.cancel();
        this.timer.purge();

        soundTask = null;
        //this.drawAccentBar();
    }*/
}
