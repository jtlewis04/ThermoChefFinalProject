package com.example.myapplication;

import static android.bluetooth.BluetoothDevice.ACTION_ACL_DISCONNECTED;
import static android.bluetooth.BluetoothDevice.ACTION_BOND_STATE_CHANGED;
import static android.bluetooth.BluetoothDevice.BOND_BONDED;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.MacAddress;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.bluetooth.*;
import android.widget.Toast;

import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    public static BluetoothAdapter defadapter;
    public static BluetoothLeScanner bluetoothScanner;
    public static Set<BluetoothDevice> foundDevices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        defadapter = BluetoothAdapter.getDefaultAdapter();

        if (defadapter != null) {
            Toast.makeText(this, "Bluetooth possible", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Bluetooth failed to initialize", Toast.LENGTH_SHORT).show();
        }
        if (defadapter.isEnabled()) {
            Toast.makeText(this, "Bluetooth initialized", Toast.LENGTH_SHORT).show();
            bluetoothScanner = defadapter.getBluetoothLeScanner();
        } else {

            Intent initializeBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            checkPerms(this);
//            int check1 = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT);
//            int check2 = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN);
//            int check3 = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH);
//            int check4 = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN);
//            int check5 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
//            int check6 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//            int baseCheck = PackageManager.PERMISSION_DENIED;
//            if (check1 == baseCheck || check2 == baseCheck || check3 == baseCheck || check4 == baseCheck || check5 == baseCheck || check6 == baseCheck) {
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 2);
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH}, 3);
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 4);
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 5);
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 6);
//            }
            startActivity(initializeBT);
            bluetoothScanner = defadapter.getBluetoothLeScanner();
        }

        btScan(this);
//        ScanSettings noFilterScan = new ScanSettings.Builder()
//                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
//                .build();
//        bluetoothScanner.startScan(null, noFilterScan, new ScanCallback() {
//            @Override
//            public void onScanResult(int callbackType, ScanResult result) {
//                super.onScanResult(callbackType, result);
//                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        return;
//                    }
//                    return;
//                }
//                if (result.getScanRecord() != null) ;
//                {
//                    List<ParcelUuid> uuids = result.getScanRecord().getServiceUuids();
//                    if (uuids != null) {
//                        bluetoothScanner.stopScan(this);
//                        for (ParcelUuid uuid : uuids) {
//                            if (uuid.getUuid().toString().startsWith("0000ffe0")) {
////                                    bluetoothScanner.stopScan(this);
//                                Log.i("Device Found", "Attempting to bond with" + result.getDevice().getName());
//                                result.getDevice().createBond();
//                            }
//                        }
//                    }
//                }
////                        foundDevices.add(result.getDevice());
//            }
//        });
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Integer.toString(BOND_BONDED))) {

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
                        foundDevices = defadapter.getBondedDevices();
                        return;
                    }
                    Log.i("Bonded Device Added", "Bonded Device:" + foundDevices.toString());
                }
                if (intent.getAction().equals(ACTION_ACL_DISCONNECTED)) {
//                    startScan
                }
            }
        };
        IntentFilter bondStatus = new IntentFilter();
        bondStatus.addAction(ACTION_BOND_STATE_CHANGED);
        bondStatus.addAction(ACTION_ACL_DISCONNECTED);
        registerReceiver(receiver, bondStatus);
        if (foundDevices != null) {
            Log.i("All Bonded Devices:", foundDevices.toString());
        }

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

    public static boolean checkPerms(Activity activity) {
        int check1 = ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT);
        int check2 = ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_ADMIN);
        int check3 = ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH);
        int check4 = ContextCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN);
        int check5 = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int check6 = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        int baseCheck = PackageManager.PERMISSION_DENIED;
        if (check1 == baseCheck || check2 == baseCheck || check3 == baseCheck || check4 == baseCheck || check5 == baseCheck || check6 == baseCheck) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 2);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH}, 3);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 4);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 5);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 6);
            return true;
    }
        else{
            return true;
        }
    }
        public static void btScan (Activity curactivity){
            checkPerms(curactivity);

            ScanSettings noFilterScan = new ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .build();

            if (ActivityCompat.checkSelfPermission(curactivity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(curactivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(curactivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.checkSelfPermission(curactivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.checkSelfPermission(curactivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            bluetoothScanner.startScan(null, noFilterScan, new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                    if (ActivityCompat.checkSelfPermission(curactivity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.checkSelfPermission(curactivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        return;
                    }
                    if (result.getScanRecord() != null) {
                        List<ParcelUuid> uuids = result.getScanRecord().getServiceUuids();
                        if (uuids != null) {
                            bluetoothScanner.stopScan(this);
                            for (ParcelUuid uuid : uuids) {
                                if (uuid.getUuid().toString().startsWith("0000ffe0")) {
//                                    bluetoothScanner.stopScan(this);
                                    Log.i("Device Found", "Attempting to bond with" + result.getDevice().getName());
                                    result.getDevice().createBond();
                                }
                            }
                        }
                    }
//                        foundDevices.add(result.getDevice());

                }
            });
        }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onSupportNavigateUp () {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            return NavigationUI.navigateUp(navController, appBarConfiguration)
                    || super.onSupportNavigateUp();
        }

}

