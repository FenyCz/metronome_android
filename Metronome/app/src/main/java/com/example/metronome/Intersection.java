package com.example.metronome;

import android.content.Context;

public class Intersection {

    public float beatX = 1;
    boolean first_beat = false;

    public boolean compare(float currentBeatX, float currentBeatY, Context context){

        if (currentBeatX == 880) {
            //if (beatX == 10 || beatX == 7 || beatX == 0 || beatX == 8 || beatX == 9 ) {

            if(!first_beat){
                first_beat = true;
                return currentBeatY >= 800;
            }

            if (beatX == 9){
                beatX += 1;
                return currentBeatY >= 800;
            }

            else if (beatX == 10){
                beatX = 0;
                return false;
            }

                /*beatX += 1;
                return currentBeatY >= 800;
            }*/
            else {
                beatX += 1;
                return false;
            }
        }

        else {
            if (currentBeatY >= 800)
            {
                return true;
            }

            else {
                return false;
            }

        }
    }
}
