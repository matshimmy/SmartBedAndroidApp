package com.example.hospitalbedcontrols.ble

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.hospitalbedcontrols.model.BluetoothViewModel

@SuppressLint("MissingPermission")
fun bluetoothEnable(app: AppCompatActivity, vm: BluetoothViewModel): Boolean {
    if (vm.isEnabled()) return true
    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
    app.startActivityForResult(enableBtIntent, 0)
    return false
}
