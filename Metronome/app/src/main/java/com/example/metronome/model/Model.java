package com.example.metronome.model;

import android.os.Parcel;
import android.os.Parcelable;
//import com.androidx.databinding.library.baseAdapters.BR;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.databinding.adapters.SeekBarBindingAdapter;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.Ignore;

import com.sdsmdg.harjot.crollerTest.Croller;

public class Model extends BaseObservable implements Parcelable {

    private int id;
    private int bpm;
    private int seekValue;
    private long freq;
    private String songName = "";
    private String accentArr = "";
    private int countOfBeats = 0;
    private String soundName = "";
    private int ordNumb = -1;
    private long timee;
    private int nTouch = 2;
    private boolean accent = false;

    //constructor
    public Model(){
    }

    //funkce pro parcelování
    protected Model(Parcel in) {
        bpm = in.readInt();
        freq = in.readLong();
        songName = in.readString();
        accentArr = in.readString();
        countOfBeats = in.readInt();
        soundName = in.readString();
        id = in.readInt();
    }

    public static Creator<Model> CREATOR = new Creator<Model>() {
        @Override
        public Model createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Model[] newArray(int size) {
            return new Model[0];
        }
    };

    //nastavení BPM, hlídá povolené meze
    public void setBpm(@NonNull int bpm) {
        if (bpm < 1 ){
            bpm = 1;
        }
        else if(bpm > 300){
            bpm = 300;
        }
        this.bpm = bpm;
        this.newBpmToFreq();
        notifyPropertyChanged(BR.bpm);
    }

    public void setSeekValue(){
        this.seekValue = getBpm();
        notifyPropertyChanged(BR.seekValue);
    }

    @Bindable
    public int getSeekValue(){ return this.seekValue; }

    //pomocna funkce bpm -> freq
    public void newBpmToFreq(){
        this.freq = 60000/this.bpm;
    }

    //gettry a settry

    public void setAccent(boolean accent){ this.accent = accent; }

    public boolean getAccent(){ return this.accent; }

    public void setNTouch(int touch){ this.nTouch += touch; }

    public long getNTouch() { return this.nTouch; }

    public void setTimee(long time){ this.timee = time; }

    public long getTimee() { return this.timee; }

    @Bindable
    public long getFreq() {
        return this.freq;
    }

    @Bindable
    public int getBpm() {
        return this.bpm;
    }

    @Bindable
    public String getSongName(){ return this.songName; }

    public String getAccentArr(){return this.accentArr;}

    public int getCountOfBeats(){return this.countOfBeats;}

    public String getSoundName(){return this.soundName;}

    public int getOrdNumb() {return this.ordNumb;}

    public int getId(){return this.id;}

    public void setOrdNumb(int ordNumb){ this.ordNumb = ordNumb;}

    public void setSoundName(String soundName) {this.soundName = soundName;}

    public void setAccentArr(String accentArr){ this.accentArr = accentArr;}

    public void setCountOfBeats(int countOfBeats) {this.countOfBeats = countOfBeats;}

    public void setId(int id) {this.id = id;}

    public  void setSongName(String songName) {
        this.songName = songName;
        notifyPropertyChanged(BR.songName);
    }

    //parcelable functions
    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bpm);
        dest.writeLong(freq);
        dest.writeString(songName);
        dest.writeString(accentArr);
        dest.writeInt(countOfBeats);
        dest.writeString(soundName);
        dest.writeInt(id);
    }
}
