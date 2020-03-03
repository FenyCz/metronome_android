package com.example.metronome.model;

import android.os.Parcel;
import android.os.Parcelable;
//import com.androidx.databinding.library.baseAdapters.BR;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;


public class Model extends BaseObservable implements Parcelable {

    protected static final int MIN = 1;
    protected static final int MAX = 300;

    private int songDatabaseId;
    private int bpm;
    private long freq;
    private String songName = "";
    private String accentArr = "";
    private int countOfBeats = 0;
    private String soundName = "";
    private int ordNumb = -1;

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
        songDatabaseId = in.readInt();
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
        if (bpm < MIN ){
            bpm = MIN;
        }
        else if(bpm > MAX){
            bpm = MAX;
        }
        this.bpm = bpm;
        this.newBpmToFreq();
        notifyPropertyChanged(BR.bpm);
    }

    //pomocna funkce bpm -> freq
    public void newBpmToFreq(){
        this.freq = 60000/this.bpm;
    }

    //gettry a settry
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

    public int getSongDatabaseId(){return this.songDatabaseId;}

    public void setOrdNumb(int ordNumb){ this.ordNumb = ordNumb;}

    public void setSoundName(String soundName) {this.soundName = soundName;}

    public void setAccentArr(String accentArr){ this.accentArr = accentArr;}

    public void setCountOfBeats(int countOfBeats) {this.countOfBeats = countOfBeats;}

    public void setSongDatabaseId(int songDatabaseId) {this.songDatabaseId = songDatabaseId;}

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
        dest.writeInt(songDatabaseId);
    }
}
