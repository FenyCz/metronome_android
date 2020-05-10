package com.example.metronome.viewModel;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.preference.PreferenceManager;

import com.example.metronome.R;
import com.example.metronome.RunMetronome;
import com.example.metronome.model.Model;
import com.sdsmdg.harjot.crollerTest.Croller;

import java.util.HashMap;
import java.util.Timer;

public class MetronomeViewModel extends ViewModel{


    public Boolean increase;
    public String increaseBpm;
    public String increaseBar;
    private TextView bar;
    public Model data;
    private Fragment fragActivity;
    private SoundPool soundPool;
    private AudioAttributes audioAttributes;
    private int mSound;
    public Timer timer;
    private boolean timerIsRunning = false;

    public ToggleButton tb;
    public boolean checked = false;
    private Croller croller;
    private EditText label;
    private Spinner accentSpinner;
    private Button butFreq;
    private float tapBpm;
    public boolean playScreenOff = false;

    private GridLayout accentLayout;

    private HashMap<String, int[]> beatsSounds = null;

    private RunMetronome runMetronome = null;

    private ViewGroup accentsViewGroup;
    private int accentsNumber;

    @SuppressLint("ClickableViewAccessibility")
    public MetronomeViewModel(Fragment activity, Model model) throws IllegalAccessException {
        this.data = model;
        this.fragActivity = activity;

        // toggle button changer
        this.tb = fragActivity.getActivity().findViewById(R.id.start_button);

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
        this.audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build();

        this.soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        // set current sound from settings
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(fragActivity.getContext());
        String currentSound = pref.getString("sound", "");
        setSound(currentSound);

        // get data from shared preferences
        increase = pref.getBoolean("increase_tempo", false);
        increaseBpm = pref.getString("bpm", "");
        increaseBar = pref.getString("bar", "");

        //accents
        final ViewGroup mainLayout = (LinearLayout)fragActivity.getActivity().findViewById(R.id.con_layout);

        //set accent spinner
        accentSpinner = this.fragActivity.getActivity().findViewById(R.id.accent_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.fragActivity.getActivity().getApplicationContext(),
                R.array.accents_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accentSpinner.setAdapter(adapter);
        accentSpinner.setSelection(3);
        accentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                stopMetronome();

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
                    case 4:
                        setNumAccent(5,mainLayout);
                        break;
                    case 5:
                        setNumAccent(6,mainLayout);
                        break;
                    case 6:
                        setNumAccent(7,mainLayout);
                        break;
                    case 7:
                        setNumAccent(8,mainLayout);
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

        bar = fragActivity.getActivity().findViewById(R.id.text_view_bar);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setNumAccent(int numAccents, ViewGroup main){
        main.removeAllViews();
        for(int i = 0 ; i < numAccents ; i++){
            final View accent1 = View.inflate(fragActivity.requireActivity().getApplicationContext(), R.layout.accents, null);

            main.addView(accent1);
            accentsViewGroup = main;
            accentsNumber = numAccents;
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
                        //Toast.makeText(fragActivity.getActivity(), "This is my Toast message! " + data.getTimee() + ", " + tapBpm + ", " + data.getNTouch(), Toast.LENGTH_LONG).show();
                        //data.setBpm(Math.round(tapBpm));
                        croller.setProgress(Math.round(tapBpm));
                    }
                }
            return true;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void textChangeBpm(CharSequence s, int start, int before, int count){
        //this.label = this.fragActivity.getActivity().findViewById(R.id.label);
        //this.label.setSelection(sequence.length());

        //onPause metoda vyvola onTextChangedListener
        if(Integer.parseInt(s.toString()) == data.getBpm()){
            return;
        }

        data.setBpm(Integer.parseInt(s.toString()));

        if(this.checked){
            timer.cancel();
            timer.purge();

            for(int i = 0; i < accentsNumber; i++){
                accentsViewGroup.getChildAt(i).setForeground(null);
            }

            timer = new Timer();

            runMetronome = new RunMetronome(soundPool, mSound, accentsViewGroup, fragActivity, accentsNumber , increase, increaseBar, increaseBpm, this);

            timer.schedule(runMetronome, 300, data.getFreq());
        }
    }

    public void buttonChangeBpm(int number){
        croller.setProgress(data.getBpm() + number);
        //data.setBpm(data.getBpm() + number);
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

        //change color of accents
        for(int i = 0; i < accentsNumber; i++){
            accentsViewGroup.getChildAt(i).setForeground(null);
        }
        bar.setText("");

        timer.cancel();
        timer.purge();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void stopMetronome(){
        if(checked) {
            checked = false;
            stopSound();

            //playScreenOff = true;

            tb.setChecked(false);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void playSound(){

        timer = new Timer();

        runMetronome = new RunMetronome(soundPool, mSound, accentsViewGroup, fragActivity, accentsNumber, increase, increaseBar, increaseBpm, this);

        runMetronome.firstBeat = false;

        timer.schedule(runMetronome,0L, data.getFreq());

        //soundPool.play(mSound, 1,1,0,-1, 1);

    }

    public void setSound(String key) {

        if(key.equals("Stick")){
            mSound = soundPool.load(fragActivity.getActivity(),R.raw.wood_1,1);
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
