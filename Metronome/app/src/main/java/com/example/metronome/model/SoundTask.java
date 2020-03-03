package com.example.metronome.model;

import android.media.SoundPool;

import com.example.metronome.viewModel.CurrentBeatDraw;

import java.util.TimerTask;

public class SoundTask extends TimerTask {

    private SoundPool soundPool;
    private int[] soundIds;
    private String accentArr;
    private int countOfBeats;
    private int currentBeat = 0;

    private CurrentBeatDraw currentBeatDraw = null;

    public SoundTask(SoundPool soundPool, int[] soundIds, String accentArr, int countOfbeats) {
        this(soundPool, soundIds, accentArr, countOfbeats, 0);
    }

    public SoundTask(SoundPool soundPool, int[] soundIds, String accentArr, int countOfbeats, int currentBeat) {
        this.currentBeat = currentBeat;
        this.countOfBeats = countOfbeats;
        this.accentArr = accentArr;

        this.soundPool = soundPool;
        this.soundIds = soundIds;

        if (currentBeat >= countOfbeats || currentBeat < 0) {
            this.currentBeat = 0;
        }
    }

    //vrátí aktuální dobu
    public int getCurrentBeat() { return currentBeat; }

    //nastaví sound
    public void setSoundId(int[] soundIds) {
        this.soundIds = soundIds;
    }

    //nastaví accentarr
    public void setAccentArr(String accentArr, int countOfBeats) {
        this.accentArr = accentArr;
        this.countOfBeats = countOfBeats;

        if (currentBeat >= countOfBeats) {
            currentBeat = 0;
        }
    }

    //callback který při kliku nastavuje
    public void register(CurrentBeatDraw callback) {
        currentBeatDraw = callback;
    }

    //vykonávání tasku
    public void run(){
        String accentArr = this.accentArr;

        //když je aktualní beat větší než počet beatu v taktu překlopí se na 0
        if (currentBeat >= accentArr.length()) {
            currentBeat = 0;
        }

        //značení a odznačení aktuální doby pomocí callbacku
        if (currentBeatDraw != null) {
            if (currentBeat == 0) {
                currentBeatDraw.unmark(countOfBeats - 1);
            }
            else {
                currentBeatDraw.unmark(currentBeat - 1);
            }

            currentBeatDraw.mark(currentBeat);
        }


        int beatValue = Character.getNumericValue(accentArr.charAt(currentBeat));

        //pokud není doba vynechána přehraje se naležitý zvuk
        if (beatValue != 0) {
            soundPool.play(this.soundIds[beatValue - 1], 1.0f, 1.0f, 1, 0, 1.0f);
        }

        currentBeat++;

        if (currentBeat >= countOfBeats) {
            currentBeat = 0;
        }
    }
}
