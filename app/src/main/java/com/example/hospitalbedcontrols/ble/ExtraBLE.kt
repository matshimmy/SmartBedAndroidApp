package com.example.hospitalbedcontrols.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.hospitalbedcontrols.model.BluetoothViewModel

@SuppressLint("MissingPermission")
private fun bluetoothEnable(app: AppCompatActivity, vm: BluetoothViewModel): Boolean {
    if (vm.isEnabled()) return true
    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    app.startActivityForResult(enableBtIntent, 0)
    return false
}

@RequiresApi(Build.VERSION_CODES.S)
fun isBLEok(app: AppCompatActivity, vm: BluetoothViewModel): Boolean {
    val permissions = BTPermissions(app)
    if (!permissions.checkPermissions()) return false
    if (!vm.isEnabled()) {
        bluetoothEnable(app, vm)
        return false
    }
    return true
}
