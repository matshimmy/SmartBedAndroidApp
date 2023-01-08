package com.example.hospitalbedcontrols.model

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.hospitalbedcontrols.ble.DeviceViewModel
import com.example.hospitalbedcontrols.ble.ScanStatus
import com.example.hospitalbedcontrols.ble.ScanViewModel
import com.juul.kable.State


class BluetoothViewModel(application: Application) : AndroidViewModel(application) {

    private val bluetoothAdapter: BluetoothAdapter by lazy {
        val bluetoothManager =
            application.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }
    private val scanViewModel = ScanViewModel(application)
    lateinit var deviceViewModel: DeviceViewModel

    val connectionStatus = MutableLiveData(false)
    val scanStatus = MutableLiveData<ScanStatus>(ScanStatus.Stopped)

    init {
        scanViewModel.status.observeForever { value ->
            scanStatus.value = value
        }
        scanViewModel.advertisement.observeForever { value ->
            if (value != null) {
//                connectionStatus.value = true //for testing
                Log.d(TAG, value.name.toString())
                deviceViewModel = DeviceViewModel(application, value.address)
                deviceViewModel.connect()
                deviceViewModel.state.observeForever {
                    when (it) {
                        is State.Connected -> connectionStatus.value = true
                        else -> connectionStatus.value = false
                    }
                }
            }
        }
    }

    // Initialize and establish the BLE connection
    fun connectToBleDevice() {
        // Initialize the BLE connection and set the connection status
        scanViewModel.startScan()
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