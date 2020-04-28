package com.example.metronome;

import android.media.MediaRecorder;
import android.media.MediaRecorder.OnErrorListener;
import android.media.MediaRecorder.OnInfoListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;


// SOURCE: https://stackoverflow.com/questions/14295427/android-audio-recording-with-voice-level-visualization
public class Demo1 extends AppCompatActivity {

    public static final int REPEAT_INTERVAL = 40;
    private TextView txtRecord;

    VisualizerView visualizerView;

    MetronomeView metronomeView;

    private MediaRecorder recorder = null;

    private boolean isRecording = false;

    private Handler handler;

    private Handler metronomeHandler;

    int n = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);

        visualizerView = (VisualizerView) findViewById(R.id.visualizer);
        metronomeView = findViewById(R.id.metronomeVisualizer);

        txtRecord = (TextView) findViewById(R.id.txtRecord);
        txtRecord.setOnClickListener(recordClick);

        // create the Handler for visualizer update
        handler = new Handler();
        metronomeHandler = new Handler();
    }

    OnClickListener recordClick = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if (!isRecording) {
                // isRecording = true;

                txtRecord.setText("Stop Recording");

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
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                handler.post(updateVisualizer);
                metronomeHandler.post(updateMetronome);

            } else {

                txtRecord.setText("Start Recording");

                releaseRecorder();
            }

        }
    };

    private void releaseRecorder() {
        if (recorder != null) {
            isRecording = false; // stop recording
            handler.removeCallbacks(updateVisualizer);
            metronomeHandler.removeCallbacks(updateMetronome);
            visualizerView.clear();
            metronomeView.clear();
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;
        }
    }

    public static boolean deleteFilesInDir(File path) {

        if( path.exists() ) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for(int i=0; i<files.length; i++) {

                if(files[i].isDirectory()) {

                }
                else {
                    files[i].delete();
                }
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        releaseRecorder();
    }

    // updates the visualizer every 50 milliseconds
    Runnable updateVisualizer = new Runnable() {
        @Override
        public void run() {
            if (isRecording) // if we are already recording
            {
                // get the current amplitude
                int x = recorder.getMaxAmplitude();
                visualizerView.addAmplitude(x); // update the VisualizeView
                visualizerView.invalidate(); // refresh the VisualizerView

                // update in 40 milliseconds
                handler.postDelayed(this, REPEAT_INTERVAL);
            }
        }
    };

    Runnable updateMetronome = new Runnable() {
        @Override
        public void run() {
            if (isRecording) // if we are already recording
            {
                // get the current amplitude
                //int x = recorder.getMaxAmplitude();

                if(n == 10){
                    metronomeView.addAmplitude(60000); // update the VisualizeView
                    metronomeView.invalidate(); // refresh the VisualizerView
                    n=0;
                }
                else{
                    metronomeView.addAmplitude(0);
                    metronomeView.invalidate(); // refresh the VisualizerView
                    n++;
                }


                // update in 40 milliseconds
                handler.postDelayed(this, REPEAT_INTERVAL);
            }
        }
    };
}
