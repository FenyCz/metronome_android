package com.example.metronome.trainer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.metronome.R;

import java.util.ArrayList;
import java.util.List;

public class VisualizerView extends View {
    private static final int LINE_WIDTH = 5; // width of visualizer lines
    private static final int LINE_SCALE = 50; // scales visualizer lines
    private List<Float> amplitudes; // amplitudes for line lengths
    private int width; // width of this View
    private int height; // height of this View
    private Paint linePaint; // specifies line drawing characteristics
    private Paint metronomeLine;

    Intersection intersection = new Intersection();
    public int beat = 30;
    public float clearHits = 0;

    int color = ContextCompat.getColor(getContext(), R.color.secondaryLightColor);

    // constructor
    public VisualizerView(Context context, AttributeSet attrs) {
        super(context, attrs); // call superclass constructor
        linePaint = new Paint(); // create Paint for lines
        linePaint.setColor(color); // set color to orange
        linePaint.setStrokeWidth(LINE_WIDTH); // set stroke width
    }

    // called when the dimensions of the View change
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w; // new width of this View
        height = h; // new height of this View
        amplitudes = new ArrayList<Float>();
    }

    // clear all amplitudes to prepare for a new visualization
    public void clear() {
        amplitudes.clear();
    }

    // add the given amplitude to the amplitudes ArrayList
    public void addAmplitude(float amplitude) {
        amplitudes.add(amplitude); // add newest to the amplitudes ArrayList

        // if the power lines completely fill the VisualizerView
        if (amplitudes.size() * LINE_WIDTH >= width) {
            amplitudes.remove(0); // remove oldest power value
        }
    }

    // draw the visualizer with scaled lines representing the amplitudes
    @SuppressLint("ShowToast")
    @Override
    public void onDraw(Canvas canvas) {
        int middle = height / 2; // get the middle of the View
        float curX = 0; // start curX at zero

        // for each item in the amplitudes ArrayList
        for (float power : amplitudes) {
            float scaledHeight = power / LINE_SCALE; // scale the power
            curX += LINE_WIDTH; // increase X by LINE_WIDTH
            float upY = middle + scaledHeight / 2;
            float downY = middle - scaledHeight / 2;

            if(curX == beat){

                boolean hit = intersection.compare(curX,upY, getContext());

                if(hit)
                {
                    clearHits += 1;
                    //Toast.makeText(getContext().getApplicationContext(),"Yes " + clearHits,Toast.LENGTH_SHORT).show();
                }

                if(beat != 910){
                    beat += 55;
                }
            }


            // draw a line representing this item in the amplitudes ArrayList
            canvas.drawLine(curX, upY , curX, downY , linePaint);
        }
    }

}
