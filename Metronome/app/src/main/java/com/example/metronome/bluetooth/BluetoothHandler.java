package com.example.metronome.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class BluetoothHandler {

    private BluetoothAdapter bluetoothAdapter;
    private Context mContext;

    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private AcceptThread acThread;

    private ConnectThread coThread;
    private BluetoothDevice bDevice;
    private UUID deviceUUID;

    private ConnectedThread cntThread;

    public BluetoothHandler(Context context) {

        mContext = context;
        bluetoothAdapter = bluetoothAdapter.getDefaultAdapter();
        start();
    }




    class AcceptThread extends Thread {

        private final String TAG = "Info";
        private BluetoothServerSocket serverSocket;

        public AcceptThread() {

            BluetoothServerSocket tmp = null;

            try {
                tmp = bluetoothAdapter.listenUsingRfcommWithServiceRecord("METRONOME", MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "Error: ", e);
                //Toast.makeText(this,"Error " + e, Toast.LENGTH_SHORT).show();
            }

            serverSocket = tmp;

        }

        // metoda ktera se automaticky vykona
        public void run() {

            BluetoothSocket socket = null;
            Log.e(TAG, "RFCOM server socket start ...");
            try {
                socket = serverSocket.accept();
                Log.e(TAG, "RFCOM server socket accepted connection.");
            } catch (IOException e) {
                Log.e(TAG, "Error2: ", e);
                //Toast.makeText(this,"Error2 " + e, Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(this,"Done", Toast.LENGTH_SHORT).show();
            if (socket != null) {
                connected(socket, bDevice);
            }

        }

        public void cancel() {
            try {
                serverSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Error2: ", e);
                //Toast.makeText(this,"Error2 " + e, Toast.LENGTH_SHORT).show();
            }
        }
    }

    class ConnectThread extends Thread{
        private BluetoothSocket socket;

        public ConnectThread(BluetoothDevice device, UUID uuid){
            bDevice = device;
            deviceUUID = uuid;
        }

        public void run(){
            BluetoothSocket tmp = null;
            try {
                tmp = bDevice.createInsecureRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e) {
                e.printStackTrace();
            }

            socket = tmp;

            bluetoothAdapter.cancelDiscovery();

            // vytvorime BluetoothSocket pripojeni

            try {
                socket.connect();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            connected(socket, bDevice);
        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void start(){
        if (coThread != null) {
            coThread.cancel();
            coThread = null;
        }

        if (acThread == null){
            acThread = new AcceptThread();
            acThread.start();
        }
    }

    public void startClient(BluetoothDevice device, UUID uuid){
        coThread = new ConnectThread(device, uuid);
        coThread.start();
    }

    private class ConnectedThread extends Thread{
        private BluetoothSocket socket;
        private InputStream inStream;
        private OutputStream outStream;

        public ConnectedThread(BluetoothSocket socket){
            this.socket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inStream = tmpIn;
            outStream = tmpOut;
        }

        public void run(){
            byte[] buffer = new byte[1024];

            int bytes;

            while (true){
                try {
                    bytes = inStream.read(buffer);
                    String incomingMessage = new String(buffer,0, bytes);
                    Log.e(TAG, "InputStream: " + incomingMessage);

                    //zasalni zpravy do aktivity
                    Intent incomingIntent = new Intent("inMessage");
                    incomingIntent.putExtra("text", incomingMessage);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(incomingIntent);

                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        public void write(byte[] bytes){
            String text = new String(bytes, Charset.defaultCharset());
            Log.e(TAG, "Outputstream: " + text);
            try {
                outStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void cancel(){
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void connected(BluetoothSocket socket, BluetoothDevice bDevice) {
        cntThread = new ConnectedThread(socket);
        cntThread.start();
    }

    public void write(byte[] out){
        ConnectedThread r;

        cntThread.write(out);
    }
}