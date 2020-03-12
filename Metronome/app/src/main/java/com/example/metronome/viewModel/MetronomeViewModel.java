package com.example.metronome.viewModel;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.metronome.R;
import com.example.metronome.RunMetronome;
import com.example.metronome.model.Model;
import com.sdsmdg.harjot.crollerTest.Croller;

import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;

public class MetronomeViewModel extends ViewModel{


    public Model data;
    private Fragment fragActivity;
    private SoundPool soundPool;
    private AudioAttributes audioAttributes;
    private int mSound;
    private Timer timer;
    private boolean timerIsRunning = false;

    private boolean checked = false;
    private Croller croller;
    private EditText label;
    private Spinner accentSpinner;
    private Button butFreq;
    private float tapBpm;

    private GridLayout accentLayout;

    private HashMap<String, int[]> beatsSounds = null;

    private RunMetronome runMetronome = null;

    @SuppressLint("ClickableViewAccessibility")
    public MetronomeViewModel(Fragment activity, Model model) throws IllegalAccessException {
        this.data = model;
        this.fragActivity = activity;

        //set default croller data
        this.croller = (Croller) this.fragActivity.getActivity().findViewById(R.id.croller);
        this.croller.setMax(300); //max number
        this.croller.setProgress(data.getBpm()); //default number of seek bar
        this.croller.setMin(1);

        //set croller listener
        this.label = this.fragActivity.getActivity().findViewById(R.id.label);

        this.croller.setOnProgressChangedListener(new Croller.onProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                label.setText("" + progress, EditText.BufferType.EDITABLE);
                data.setBpm(progress);
            }
        });

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

        //accents
        final ViewGroup mainLayout = (LinearLayout)fragActivity.getActivity().findViewById(R.id.con_layout);

        //set accent spinner
        accentSpinner = this.fragActivity.getActivity().findViewById(R.id.accent_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.fragActivity.getActivity().getApplicationContext(),
                R.array.accents_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accentSpinner.setAdapter(adapter);
        accentSpinner.setSelection(3);
        accentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        setNumAccent(1,mainLayout);
                        break;
                    case 1:
                        setNumAccent(2,mainLayout);
                        break;
                    case 2:
                        setNumAccent(3,mainLayout);
                        break;
                    case 3:
                        setNumAccent(4,mainLayout);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //TODO : accent bar
        //accentBar = this.activity.findViewById(R.id.accentBar);


        //TODO continue - comment me
        /*beatsSounds = SoundLoader.loadSounds(this.activity, this.soundPool);

        Spinner spinner = this.activity.findViewById(R.id.spinner_beat_sound);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this.activity, android.R.layout.simple_spinner_item,
                        beatsSounds.keySet().toArray(new String[(beatsSounds.size())]));

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setSelection(getIndex(spinner,this.data.getSoundName()));*/
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setNumAccent(int numAccents, ViewGroup main){
        main.removeAllViews();
        for(int i = 0 ; i < numAccents ; i++){
            final View accent1 = View.inflate(Objects.requireNonNull(fragActivity.getActivity()).getApplicationContext(), R.layout.accents, null);

            final Drawable ac1 = fragActivity.getActivity().getDrawable(R.drawable.circle_accent);
            final Drawable ac2 = fragActivity.getActivity().getDrawable(R.drawable.circle_accent_default);

            View view = accent1.findViewById(R.id.accent);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!data.getAccent()){
                        v.setBackground(ac1);
                        data.setAccent(true);}
                    else{
                        v.setBackground(ac2);
                        data.setAccent(false);
                    }
                }
            });
            main.addView(accent1);
        }
    }

    //set bpm by tapping on button
    @SuppressLint("ClickableViewAccessibility")
    public void buttonTapBpm(){

        this.butFreq = this.fragActivity.getActivity().findViewById(R.id.button_repeat);

        butFreq.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP) {

                    if(data.getNTouch() % 2 == 0) {
                        data.setTimee(System.currentTimeMillis());
                        data.setNTouch(1);
                    }
                    else{
                        tapBpm = System.currentTimeMillis() - data.getTimee();
                        tapBpm = tapBpm/10;
                        tapBpm = 6000/tapBpm;
                        data.setNTouch(1);
                        Toast.makeText(fragActivity.getActivity(), "This is my Toast message! " + data.getTimee() + ", " + tapBpm + ", " + data.getNTouch(), Toast.LENGTH_LONG).show();
                        data.setBpm(Math.round(tapBpm));
                        croller.setProgress(data.getBpm());
                    }
                }
            return true;
            }
        });
    }

    public void textChangeBpm(CharSequence sequence){
        //this.label = this.fragActivity.getActivity().findViewById(R.id.label);
        //this.label.setSelection(sequence.length());

        if(this.checked){
            timer.cancel();
            timer.purge();

            this.timer = new Timer();

            this.runMetronome = new RunMetronome(soundPool, mSound);

            this.timer.schedule(this.runMetronome, 0L, data.getFreq());
        }

        /*croller = this.fragActivity.getActivity().findViewById(R.id.croller);
        //TODO: hotovo - smazat

        //circular seek bar
        croller = getView().findViewById(R.id.croller);
        bpmText = getView().findViewById(R.id.label);

        croller.setOnProgressChangedListener(new Croller.onProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress) {
                bpmText.setText("" + progress, EditText.BufferType.EDITABLE);
            }
        });*/
    }

    public void buttonChangeBpm(int number){
        this.data.setBpm(this.data.getBpm() + number);
        croller.setProgress(this.data.getBpm());
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

    private void stopSound() {
        //soundPool.release();
        //soundPool = null;
        this.timer.cancel();
        this.timer.purge();
    }

    public void playSound(){

        this.timer = new Timer();

        this.runMetronome = new RunMetronome(soundPool, mSound);

        this.timer.schedule(this.runMetronome,0L, data.getFreq());

        //soundPool.play(mSound, 1,1,0,-1, 1);

    }

    /*public void setDefault() {
        Croller croller = (Croller) this.fragActivity.getActivity().findViewById(R.id.croller);
        //croller.setMax(300); //max number
        //croller.setProgress(data.getBpm()); //default number of seek bar
        //croller.setMin(1);
    }*/

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
            Spinner spinner = this.fragActivity.getView().findViewById(R.id.croller);
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