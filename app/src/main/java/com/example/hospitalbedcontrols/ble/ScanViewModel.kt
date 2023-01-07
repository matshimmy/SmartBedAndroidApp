package com.example.hospitalbedcontrols.ble

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.juul.kable.Advertisement
import com.juul.kable.Scanner
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeUnit

private val SCAN_DURATION_MILLIS = TimeUnit.SECONDS.toMillis(5)

sealed class ScanStatus {
    object Stopped : ScanStatus()
    object Scanning : ScanStatus()
    data class Failed(val message: CharSequence) : ScanStatus()
}

class ScanViewModel(application: Application) : AndroidViewModel(application) {
    private val scanner = Scanner()
    private val scanScope = CoroutineScope(Dispatchers.IO)

    private val _status = MutableStateFlow<ScanStatus>(ScanStatus.Stopped)
    val status = _status.asStateFlow()

    private val _advertisements = MutableStateFlow<List<Advertisement>>(emptyList())
    val advertisements = _advertisements.asStateFlow()

    fun startScan() {
        if (_status.value == ScanStatus.Scanning) return // Scan already in progress
        _status.value = ScanStatus.Scanning
        scanScope.launch {
            Log.d(TAG, "startScan: starting")
            withTimeoutOrNull(SCAN_DURATION_MILLIS) {
                scanner
                    .advertisements
                    .catch { cause ->
                        _status.value = ScanStatus.Failed(cause.message ?: "Unknown error")
                    }
                    .onCompletion { cause ->
                        if (cause == null || cause is CancellationException) _status.value =
                            ScanStatus.Stopped
                    }
                    .filter { it.isDSD }
                    .collect { advertisement ->
                        Log.d(TAG, advertisement.name.toString())
                    }
            }
        }
    }
}

private val Advertisement.isDSD get() = name?.startsWith("DSD") == true

private const val TAG = "ScanViewModel"