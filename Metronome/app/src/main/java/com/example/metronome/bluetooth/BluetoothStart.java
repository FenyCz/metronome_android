package com.example.metronome.bluetooth;


public class BluetoothStart {

    boolean flagConnected = false;

    BluetoothHandler bluetoothHandler;

    private static final BluetoothStart bluetoothStart = new BluetoothStart();
    public static BluetoothStart getInstance() {return bluetoothStart;}

}
