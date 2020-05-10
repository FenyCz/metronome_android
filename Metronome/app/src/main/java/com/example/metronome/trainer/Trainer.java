package com.example.metronome.trainer;

import android.media.AudioManager;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.metronome.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


// SOURCE: https://stackoverflow.com/questions/14295427/android-audio-recording-with-voice-level-visualization
public class Trainer extends AppCompatActivity {

    public static final int REPEAT_INTERVAL = 40;
    public TextView txtRecord;

    VisualizerView visualizerView;

    MetronomeView metronomeView;

    private MediaRecorder recorder = null;

    private boolean isRecording = false;

    private Handler handler;

    private Handler metronomeHandler;

    int n = 5;
    int k = 0;
    float allHits = 0;
    private Timer timer;
    private TimerTask timerTask;

    SoundPool soundPool;
    int soundId;

    public TextView txtPercents;
    public TextView txtHits;
    public TextView txtCounting;
    public int startCounting = 6;

    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);

        visualizerView = findViewById(R.id.visualizer);
        metronomeView = findViewById(R.id.metronomeVisualizer);

        txtRecord = findViewById(R.id.txtRecord);
        txtRecord.setOnClickListener(recordClick);
        txtHits = findViewById(R.id.txtHits);
        txtPercents = findViewById(R.id.txtPercents);
        txtCounting = findViewById(R.id.txtCounting);

        // create the Handler for visualizer update
        handler = new Handler();
        metronomeHandler = new Handler();

        // sound pool
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundId = soundPool.load(getApplicationContext(), R.raw.stick, 1);

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

    }

    OnClickListener recordClick = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (!isRecording) {
                // isRecording = true;

                txtRecord.setText("Stop");

                recorder = new MediaRecorder();

                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                recorder.setOutputFile("/dev/null");

                OnErrorListener errorListener = null;
                recorder.setOnErrorListener(errorListener);
                OnInfoListener infoListener = null;
                recorder.setOnInfoListener(infoListener);

                try {
                    recorder.prepare();
                    recorder.start();
                    isRecording = true; // we are currently recording
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }

                //handler.post(updateVisualizer);
                metronomeHandler.post(updateMetronome);

            } else {

                txtRecord.setText("Start");

                txtHits.setText("Hits: " + String.format("%.0f",visualizerView.clearHits) + " / " + String.format("%.0f",allHits));

                float percentResult = (visualizerView.clearHits / allHits) * 100;

                String myFloat = String.format("%.1f",percentResult);

                txtPercents.setText(myFloat + "%");

                txtCounting.setText("5");

                startCounting = 6;

                //Toast.makeText(getApplicationContext(),visualizerView.clearHits + "/" + allHits + "- " + percentResult + "%",Toast.LENGTH_SHORT).show();

                releaseRecorder();
            }

        }
    };

    private void releaseRecorder() {
        if (recorder != null) {
            isRecording = false; // stop recording

            //handler.removeCallbacks(updateVisualizer);
            metronomeHandler.removeCallbacks(updateMetronome);

            timer.cancel();
            timer.purge();

            //timer1.cancel();
            //timer1.purge();

            visualizerView.clear();
            visualizerView.beat = 30;
            visualizerView.clearHits = 0;
            n = 5;
            visualizerView.intersection.beatX = 0;
            allHits = 0;

            metronomeView.clear();

            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        releaseRecorder();

        soundPool.release();
        soundPool = null;
    }

    final Runnable updateMetronome = new Runnable() {
        @Override
        public void run() {
            if (isRecording) // if we are already recording
            {

                txtPercents.setText("");
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {

                        runOnUiThread(new TimerTask() {
                            @Override
                            public void run() {

                                // start of countdown
                                if(startCounting != 0){

                                    if(k == 10){
                                        startCounting--;
                                        k=0;
                                        if(startCounting != 0) {
                                            soundPool.play(soundId, 1, 1, 0, 0, 1);
                                            txtCounting.setText(String.valueOf(startCounting));
                                        }
                                    }

                                    else {
                                        k++;
                                    }
                                }

                                // get the current amplitude
                                //int x = recorder.getMaxAmplitude();

                                // start scanning hits
                                else {

                                    txtCounting.setText("");

                                    try {
                                        x = recorder.getMaxAmplitude();
                                    } catch (Exception ignored) {
                                        ;
                                    }

                                    visualizerView.addAmplitude(x); // update the VisualizeView
                                    visualizerView.invalidate(); // refresh the VisualizerView

                                    if (n == 5) {
                                        soundPool.play(soundId, 1, 1, 0, 0, 1);
                                    }

                                    if (n == 10) {
                                        metronomeView.addAmplitude(40000); // update the VisualizeView
                                        metronomeView.invalidate(); // refresh the VisualizerView

                                        n = 0;
                                        allHits += 1;
                                        txtHits.setText("Hits: " + String.format("%.0f", visualizerView.clearHits) + " / " + String.format("%.0f", allHits));
                                    }

                                    else {
                                        metronomeView.addAmplitude(0);
                                        metronomeView.invalidate(); // refresh the VisualizerView
                                        n++;
                                    }
                                }
                            }
                        });
                    }
                }, 0, (long) ((55300.0/150.0)/10.0));
            }
        }
    };
}
