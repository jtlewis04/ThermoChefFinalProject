package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
public class Bluetooth {
    public static final int BLUETOOTH_REQUEST = 3;
    public static final int BLUETOOTH_ADMIN_REQUEST = 2;
    public static final int BLUETOOTH_SCAN_REQUEST = 1;
    public static final int BLUETOOTH_CONNECT_REQUEST = 4;
    private static BluetoothAdapter defAdapter;
//    public static void bluetoothScan()
//    {
//    defAdapter = MainActivity.defadapter;
//    BluetoothLeScanner scan = defAdapter.getBluetoothLeScanner();
//
//    scan.startScan(ScanSettings.CALLBACK_TYPE_ALL_MATCHES);
//    ScanResult
////    ScanCallback callback = scan.
//    }
    public static void checkPerms(Context context, Activity activity)
    {
        int check1 = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT);
        int check2 = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN);
        int check3 = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);
        int check4 = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN);
        int baseCheck = PackageManager.PERMISSION_DENIED;
            if(check1 == baseCheck && check2 == baseCheck && check3 == baseCheck && check4 == baseCheck)
        {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 1);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_ADMIN}, 2);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH}, 3);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH_CONNECT}, 4);
        }
    }
}

