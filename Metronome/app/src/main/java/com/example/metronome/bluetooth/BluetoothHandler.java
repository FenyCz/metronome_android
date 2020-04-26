package com.example.metronome.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.metronome.R.color.secondaryLightColor;

public class BluetoothHandler {

    private BluetoothAdapter bluetoothAdapter;
    private Context mContext;

    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private AcceptThread acThread;

    private BluetoothDevice bDevice;

    private ConnectedThread cntThread;

    private BluetoothSocket connectedSocket;
    BluethootActivity bluethootActivity;

    public BluetoothHandler(Context context, BluethootActivity bluethootActivity) {

        mContext = context;
        this.bluethootActivity = bluethootActivity;
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

            bluethootActivity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(bluethootActivity, "Waiting for connection ...", Toast.LENGTH_SHORT).show();
                }
            });

            while(true) {
                try {
                    socket = serverSocket.accept();
                    Log.e(TAG, "RFCOM server socket accepted connection.");

                    bluethootActivity.runOnUiThread(new Runnable()
                    {
                        public void run()
                        {
                            Toast.makeText(bluethootActivity,"Connected!", Toast.LENGTH_SHORT).show();
                            bluethootActivity.inMessage.setText("Connected!");
                            bluethootActivity.inMessage.setBackgroundColor(Color.parseColor("#009900"));
                            bluethootActivity.sendPlaylists.setBackgroundColor(Color.parseColor("#009900"));
                            bluethootActivity.disconnect.setBackgroundColor(Color.parseColor("#009900"));
                            bluethootActivity.bluetoothStart.flagConnected = true;
                        }
                    });

                } catch (IOException e) {
                    Log.e(TAG, "Error2: ", e);
                    break;
                    //Toast.makeText(this,"Error2 " + e, Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(this,"Done", Toast.LENGTH_SHORT).show();
                if (socket != null) {
                    connected(socket, bDevice);
                    cancel();
                    break;
                }
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

    public synchronized void start(){

        if (acThread == null){
            acThread = new AcceptThread();
            acThread.start();
        }
    }

    /*public void startClient(BluetoothDevice device, UUID uuid){
        coThread = new ConnectThread(device, uuid);
        coThread.start();
    }*/

    private class ConnectedThread extends Thread {

        private InputStream inStream;
        private OutputStream outStream;

        public ConnectedThread(BluetoothSocket socket) {
            connectedSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = connectedSocket.getInputStream();
                tmpOut = connectedSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inStream = tmpIn;
            outStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];

            int bytes;

            while (true) {
                try {
                    bytes = inStream.read(buffer);
                    String incomingMessage = new String(buffer, 0, bytes);
                    Log.e(TAG, "InputStream: " + incomingMessage);

                    //zasalni zpravy do aktivity
                    Intent incomingIntent = new Intent("inMessage");
                    incomingIntent.putExtra("text", incomingMessage);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(incomingIntent);

                } catch (IOException e) {
                    cancel();
                    e.printStackTrace();
                    break;
                }
            }
        }

        public void write(byte[] bytes) {
            String text = new String(bytes, Charset.defaultCharset());
            Log.e(TAG, "Outputstream: " + text);
            try {
                outStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // kdyz dojde k odpojeni klienta od serveru a ukonceni spojeni
    public void cancel(){
        try {
            connectedSocket.close();
            bluethootActivity.runOnUiThread(new Runnable()
            {
                public void run()
                {
                    bluethootActivity.inMessage.setBackgroundColor(mContext.getResources().getColor(secondaryLightColor));
                    bluethootActivity.sendPlaylists.setBackgroundColor(mContext.getResources().getColor(secondaryLightColor));
                    bluethootActivity.disconnect.setBackgroundColor(mContext.getResources().getColor(secondaryLightColor));
                    Toast.makeText(bluethootActivity,"Disconnected!", Toast.LENGTH_SHORT).show();
                    bluethootActivity.bluetoothStart.flagConnected = false;
                    bluethootActivity.flagFirstClick = false;
                    bluethootActivity.inMessage.setText("");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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