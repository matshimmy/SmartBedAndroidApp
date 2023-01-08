package com.example.hospitalbedcontrols.ble

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.juul.kable.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.TimeMark

sealed class ViewState {
    object Connecting : ViewState()

    object Connected : ViewState()

    object Disconnecting : ViewState()

    object Disconnected : ViewState()
}

val ViewState.label: CharSequence
    get() = when (this) {
        ViewState.Connecting -> "Connecting"
        is ViewState.Connected -> "Connected"
        ViewState.Disconnecting -> "Disconnecting"
        ViewState.Disconnected -> "Disconnected"
    }

class DeviceViewModel(application: Application, macAddress: String) : AndroidViewModel(application) {
    private val peripheral = viewModelScope.peripheral(macAddress)

    @OptIn(ExperimentalCoroutinesApi::class)
    val viewState: Flow<ViewState> = peripheral.state.flatMapLatest { state ->
        when (state) {
            is State.Connecting -> flowOf(ViewState.Connecting)
            State.Connected -> flowOf(ViewState.Connected)
            State.Disconnecting -> flowOf(ViewState.Disconnecting)
            is State.Disconnected -> flowOf(ViewState.Disconnected)
        }
    }

    val state = viewState.asLiveData()

    //HM-10 Char
    private val DSDCharacteristic = characteristicOf(
        service = "0000ffe0-0000-1000-8000-00805f9b34fb",
        characteristic = "0000ffe1-0000-1000-8000-00805f9b34fb"
    )

    private val writeData = byteArrayOf(0x54, 0x65, 0x73, 0x74)// ascii Test


    @OptIn(ExperimentalTime::class)
    private var startTime: TimeMark? = null



    fun connect() {
        viewModelScope.connect()

    }

    fun disconnect() {
        viewModelScope.disconnect()
    }

    fun writeData() {
        viewModelScope.write()
    }

    fun discoverData() {
        if (peripheral.state.value != State.Connected){
            Log.e(TAG, "discoverData: Cannot discover data without BLE connection")
            return
        }
        peripheral.services?.forEach { service ->
            Log.d(TAG, "service of peripheral: ${service.serviceUuid}")
            service.characteristics.forEach { chstic ->
                Log.d(TAG, "Characteristic id: ${chstic.characteristicUuid}")
            }
        }
    }

    private fun CoroutineScope.disconnect() {
        launch {
            Log.d(TAG, "disconnect")
            peripheral.disconnect()
        }
    }

    private fun CoroutineScope.write() {
        launch {
            Log.d(TAG, "write")
            peripheral.write(DSDCharacteristic, writeData, WriteType.WithoutResponse)
        }
    }

    private fun CoroutineScope.connect() {
        launch {
            Log.d(TAG, "connect")
            try {
                peripheral.connect()
            } catch (e: ConnectionLostException) {
                Log.e(TAG, "Connection Attempt failed")
            }
        }
    }
}

private const val TAG = "DeviceViewModel"