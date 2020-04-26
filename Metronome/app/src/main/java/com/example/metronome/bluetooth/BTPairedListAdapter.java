package com.example.metronome.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.metronome.R;

import java.util.ArrayList;


public class BTPairedListAdapter extends ArrayAdapter<BluetoothDevice> {

    private LayoutInflater layoutInflater;
    private ArrayList<BluetoothDevice> devices;
    private int  viewResourceId;
    private BluetoothDevice bDevice;
    private BluetoothHandler bHandler;

    public BTPairedListAdapter(Context context, int rId, ArrayList<BluetoothDevice> devices, BluetoothDevice bDevice, BluetoothHandler bluetoothHandler){
        super(context, rId,devices);
        this.devices = devices;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewResourceId = rId;

        bHandler = bluetoothHandler;
        this.bDevice = bDevice;
    }

    @SuppressLint("ViewHolder")
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(viewResourceId, null);

        BluetoothDevice device = devices.get(position);

        if (device != null) {

            TextView pairedName = (TextView) convertView.findViewById(R.id.pairedName);

            if (pairedName != null) {
                pairedName.setText(device.getName());
            }
        }

        /*convertView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                //bDevice = devices.get(position);
                //bHandler = new BluetoothHandler(getContext().getApplicationContext());
                v.setBackgroundColor(secondaryColor);
            }
        });*/

        return convertView;
    }

}
