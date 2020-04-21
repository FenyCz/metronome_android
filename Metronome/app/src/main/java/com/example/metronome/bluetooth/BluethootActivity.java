package com.example.metronome.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.metronome.R;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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

    BluetoothHandler bluetoothHandler;

    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BluetoothDevice bDevice;

    TextView inMessage;
    StringBuilder message;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluethoot);
        listNewDevices = (ListView) findViewById(R.id.list_new_devices);
        listPairedDevices = (ListView) findViewById(R.id.list_paired_devices);
        editTextSend = findViewById(R.id.editTextSend);

        inMessage = findViewById(R.id.textViewChat);
        message = new StringBuilder();

        LocalBroadcastManager.getInstance(this).registerReceiver(mesReceiver, new IntentFilter("inMessage"));
    }

    BroadcastReceiver mesReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("text");

            message.append(text + "\n");

            inMessage.setText(message);
        }
    };

    /*public void makeBTconnection(BluetoothDevice device, UUID uuid){
        bluetoothHandler.startClient(device,uuid);
    }*/

    public void sendData(View view) {
        byte[] bytes = editTextSend.getText().toString().getBytes(Charset.defaultCharset());
        bluetoothHandler.write(bytes);

        editTextSend.setText("");
    }

        /*BluetoothGattServerCallback bluetoothGattServerCallback= new BluetoothGattServerCallback() {
            @Override
            public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
                super.onConnectionStateChange(device, status, newState);
            }

            @Override
            public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
                super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
            }

            @Override
            public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
                super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
            }

            @Override
            public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
                super.onDescriptorReadRequest(device, requestId, offset, descriptor);
            }

            @Override
            public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
                super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite, responseNeeded, offset, value);
            }
        };*/

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void FoundedDevices(View view) {

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
        Toast.makeText(this, "Discovering devices...", Toast.LENGTH_SHORT).show();


        // vyhledat zarizeni
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        if(bluetoothAdapter.isDiscovering())
        {
            bluetoothAdapter.cancelDiscovery();
        }

        bluetoothAdapter.startDiscovery();

        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
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

        // zobrazit sparovana zarizeni
        final Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (final BluetoothDevice device : pairedDevices) {

                if (!hp.contains(device)) {
                    hp.add(device);

                    btPaired.add(device);
                    btPairedListAdapter = new BTPairedListAdapter(getApplicationContext(), R.layout.btpaired_adapter, btPaired, bDevice, bluetoothHandler);
                    listPairedDevices.setAdapter(btPairedListAdapter);
                    listPairedDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            //bDevice = device; //FAILURE
                            bDevice = btPaired.get(position);
                            bluetoothHandler = new BluetoothHandler(getApplicationContext());
                        }
                    });
                }
            }
        }
    }

        //while(serverSocket==null) {
            //new AcceptThread(bluetoothAdapter);
            //new AcceptThread().run();
        //}

        /*bluetoothAdvertiser = bluetoothAdapter.getBluetoothLeAdvertiser();
        gattServer = bluetoothManager.openGattServer(this, bluetoothGattServerCallback);
        BluetoothGattService service = new BluetoothGattService(UUID.fromString("d5925e86-7a88-11ea-bc55-0242ac130003"), BluetoothGattService.SERVICE_TYPE_PRIMARY);

        BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(UUID.fromString("275348FB-C14D-4FD5-B434-7C3F351DEA5F"),
                BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_WRITE |
                        BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                BluetoothGattCharacteristic.PERMISSION_READ | BluetoothGattCharacteristic.PERMISSION_WRITE);

        characteristic.addDescriptor(new BluetoothGattDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"), BluetoothGattCharacteristic.PERMISSION_WRITE));

        service.addCharacteristic(characteristic);

        gattServer.addService(service);*/

    /*@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startAdvertising(){
        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setConnectable(true)
                .build();

        AdvertiseData advertiseData = new AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .setIncludeTxPowerLevel(true)
                .build();

        AdvertiseData scanResponseData = new AdvertiseData.Builder()
                .addServiceUuid(new ParcelUuid(your_service_uuid))
                .setIncludeTxPowerLevel(true)
                .build();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private AdvertiseCallback callback = new AdvertiseCallback() {
        private static final String TAG = "Activity";

        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            Log.d(TAG, "BLE advertisement added successfully");
        }

        @Override
        public void onStartFailure(int errorCode) {
            Log.e(TAG, "Failed to add BLE advertisement, reason: " + errorCode);
        }
    };*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bluetoothAdapter.cancelDiscovery();
        //unregisterReceiver(receiver);
    }

    /*public void startConnection(View view) {
        strConnection();
    }*/

    /*public void strConnection(){
        makeBTconnection(bDevice,MY_UUID);
    }*/

    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }*/
}
