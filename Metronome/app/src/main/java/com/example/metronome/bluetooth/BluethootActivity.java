package com.example.metronome.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.metronome.R;
import com.example.metronome.playlistdatabase.Playlist;
import com.example.metronome.playlistdatabase.PlaylistDatabase;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.example.metronome.R.color.secondaryLightColor;

public class BluethootActivity extends AppCompatActivity {

    private static int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter bluetoothAdapter;

    private BroadcastReceiver receiver;

    public ArrayList<BluetoothDevice> btDevices = new ArrayList<>();
    public BTDeviceListAdapter btDeviceListAdapter;
    ListView listNewDevices;

    ArrayList<BluetoothDevice> btPaired = new ArrayList<>();
    public BTPairedListAdapter btPairedListAdapter;
    ListView listPairedDevices;

    HashSet<String> ha = new HashSet<String>();
    HashSet<BluetoothDevice> hp = new HashSet<BluetoothDevice>();

    EditText editTextSend;

    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BluetoothDevice bDevice;

    TextView inMessage;
    StringBuilder message;
    private boolean isRegistred = false;

    BluetoothStart bluetoothStart = BluetoothStart.getInstance();

    boolean flagFirstClick = false;

    Button sendPlaylists;
    Button disconnect;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluethoot);
        listNewDevices = (ListView) findViewById(R.id.list_new_devices);
        listPairedDevices = (ListView) findViewById(R.id.list_paired_devices);
        sendPlaylists = findViewById(R.id.buttonSend);
        disconnect = findViewById(R.id.buttonDisconnect);

        inMessage = findViewById(R.id.textViewChat);
        message = new StringBuilder();

        LocalBroadcastManager.getInstance(this).registerReceiver(mesReceiver, new IntentFilter("inMessage"));

        if(bluetoothStart.flagConnected){
            inMessage.setText("Connected!");
            inMessage.setBackgroundColor(Color.parseColor("#009900"));
            sendPlaylists.setBackgroundColor(Color.parseColor("#009900"));
            disconnect.setBackgroundColor(Color.parseColor("#009900"));
            bluetoothStart.newActivity = this;
        }
    }

    BroadcastReceiver mesReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("text");

            message.append(text + "\n");

            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            //inMessage.setText(message);
        }
    };

    public void sendData(View view) {
        //byte[] bytes = editTextSend.getText().toString().getBytes(Charset.defaultCharset());
        //bluetoothStart.bluetoothHandler.write(bytes);

        // database builder
        PlaylistDatabase pdb = PlaylistDatabase.getInstance(getApplicationContext());

        // list for database songs
        ArrayList<Playlist> listPlaylist = (ArrayList<Playlist>) pdb.playlistDao().getAllPlaylists();

        if(listPlaylist.size() != 0){
            for(int i = 0; i < listPlaylist.size(); i++){
                String pData = "P*";
                String pName = listPlaylist.get(i).playlist + "#";

                pData += pName;

                byte[] plBytes = pData.getBytes(Charset.defaultCharset());
                bluetoothStart.bluetoothHandler.write(plBytes);

                for(int o = 0; o < listPlaylist.get(i).playlistSongs.size(); o++)
                {
                    String sData = "S*";
                    String sName = listPlaylist.get(i).playlistSongs.get(o).getSongName() + "*";
                    String sTempo = listPlaylist.get(i).playlistSongs.get(o).getBpm() + "*";
                    String sPlaylist = listPlaylist.get(i).playlist + "#";

                    sData = sData + sName + sTempo + sPlaylist;

                    byte[] soBytes = sData.getBytes(Charset.defaultCharset());
                    bluetoothStart.bluetoothHandler.write(soBytes);
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Find(View view) {

        // inicializace BT adapteru
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not support.", Toast.LENGTH_SHORT).show();
            return;
        }

        // kontrola jestli je BT zapnuty, pokud ne vyskoci dialogove okno pro jeho zapnuti
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // umoznit, aby bylo zarizeni viditelne pomoci bluetooth
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        // vyhledat zarizeni
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        if(bluetoothAdapter.isDiscovering())
        {
            bluetoothAdapter.cancelDiscovery();
        }

        bluetoothAdapter.startDiscovery();
        Toast.makeText(this, "Discovering devices...", Toast.LENGTH_SHORT).show();

        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
            isRegistred = true;
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);


                if (!ha.contains(device.getAddress())) {
                    ha.add(device.getAddress());

                    //Toast.makeText(getApplicationContext(), "Found device " + device.getName(), Toast.LENGTH_SHORT).show();
                    btDevices.add(device);
                    btDeviceListAdapter = new BTDeviceListAdapter(context, R.layout.btdevice_adapter, btDevices);
                    listNewDevices.setAdapter(btDeviceListAdapter);
                }
            }
            }
        };
    }

    public void Paired(View view) {

        // inicializace BT adapteru + zastaveni vyhledavani zarizeni
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not support.", Toast.LENGTH_SHORT).show();
            return;
        }

        // kontrola jestli je BT zapnuty, pokud ne vyskoci dialogove okno pro jeho zapnuti
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        // umoznit, aby bylo zarizeni viditelne pomoci bluetooth
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        // zobrazit sparovana zarizeni
        final Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (final BluetoothDevice device : pairedDevices) {

                if (!hp.contains(device)) {
                    hp.add(device);

                    btPaired.add(device);
                    btPairedListAdapter = new BTPairedListAdapter(getApplicationContext(), R.layout.btpaired_adapter, btPaired, bDevice, bluetoothStart.bluetoothHandler);
                    listPairedDevices.setAdapter(btPairedListAdapter);
                    listPairedDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            if(bluetoothStart.flagConnected){
                                inMessage.setText("Already connected!");
                            }

                            else {
                                if(!flagFirstClick) {
                                    bDevice = btPaired.get(position);
                                    inMessage.setText("Server: Ready to connect to " + bDevice.getName());
                                    bluetoothStart.bluetoothHandler = new BluetoothHandler(getApplicationContext(), BluethootActivity.this);
                                    flagFirstClick = true;
                                }
                                else{
                                    bDevice = btPaired.get(position);
                                    inMessage.setText("Server: Ready to connect to " + bDevice.getName());
                                    bluetoothStart.bluetoothHandler = null;
                                    bluetoothStart.bluetoothHandler = new BluetoothHandler(getApplicationContext(), BluethootActivity.this);
                                }
                            }

                        }
                    });
                }
            }
        }

        //BluetoothStart.getInstance().Paired(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //bluetoothAdapter.cancelDiscovery();
        if(isRegistred) {
            unregisterReceiver(receiver);
        }
    }


    public void CloseConnection(View view) {
        if(!bluetoothStart.flagConnected){
            Toast.makeText(this, "Disconected!", Toast.LENGTH_SHORT).show();
            inMessage.setText("");
            inMessage.setBackgroundColor(getResources().getColor(secondaryLightColor));
            sendPlaylists.setBackgroundColor(getResources().getColor(secondaryLightColor));
            disconnect.setBackgroundColor(getResources().getColor(secondaryLightColor));
        }
        else {
            bluetoothStart.bluetoothHandler.cancel();
            bluetoothStart.flagConnected = false;
            flagFirstClick = false;
            inMessage.setText("");
            inMessage.setBackgroundColor(getResources().getColor(secondaryLightColor));
            sendPlaylists.setBackgroundColor(getResources().getColor(secondaryLightColor));
            disconnect.setBackgroundColor(getResources().getColor(secondaryLightColor));
            Toast.makeText(this, "Disconected!", Toast.LENGTH_SHORT).show();
        }
    }

    /*public void startConnection(View view) {
        strConnection();
    }*/

    /*public void strConnection(){
        makeBTconnection(bDevice,MY_UUID);
    }*/

}
