package com.example.hospitalbedcontrols.model

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class BluetoothViewModel(application: Application) : AndroidViewModel(application) {

    private val bluetoothAdapter: BluetoothAdapter by lazy {
        val bluetoothManager =
            application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    // Variable to hold the BLE connection status
    val connectionStatus = MutableLiveData<Boolean>()

    // Initialize and establish the BLE connection
    fun connectToBleDevice() {
        if (!bluetoothAdapter.isEnabled) {
            Log.e(TAG, "connectToBleDevice: Enable Bluetooth", )
            return
        }
        // Initialize the BLE connection and set the connection status
        connectionStatus.value = true
    }

    fun isEnabled(): Boolean {
        return bluetoothAdapter.isEnabled
    }

    // Perform a BLE write operation
    fun writeBleCharacteristic(data: ByteArray) {
        // Perform the BLE write operation
    }
}

private const val TAG = "BluetoothViewModel"