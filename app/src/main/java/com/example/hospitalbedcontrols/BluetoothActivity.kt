package com.example.hospitalbedcontrols

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.hospitalbedcontrols.ble.ScanStatus
import com.example.hospitalbedcontrols.ble.ViewState
import com.example.hospitalbedcontrols.ble.isBLEok
import com.example.hospitalbedcontrols.ble.label
import com.example.hospitalbedcontrols.databinding.ActivityBluetoothBinding
import com.example.hospitalbedcontrols.model.BluetoothViewModel

class BluetoothActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBluetoothBinding
    private lateinit var viewModel: BluetoothViewModel

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBluetoothBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this).get(BluetoothViewModel::class.java)


        if (viewModel.connectionStatus.value is ViewState.Connected) connected()
        viewModel.connectionStatus.observe(this) {
            when (it) {
                ViewState.Connected -> connected()
                ViewState.Disconnected -> allowScan()
                else -> working(viewModel.connectionStatus.value?.label as String)
            }
        }

        viewModel.scanStatus.observe(this) {
            when (it) {
                is ScanStatus.Scanning -> working("Scanning...")
                else -> allowScan()
            }
        }


        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.to_home -> {
                    finish()
                    true
                }

                else -> false
            }
        }

        binding.toggleConnection.setOnClickListener {
            if (isBLEok(this, viewModel))
                viewModel.connectToBleDevice()
        }
    }

    private fun allowScan() {
        if (viewModel.connectionStatus.value is ViewState.Connected) return connected()
        binding.toggleConnection.text = "Connect"
        binding.toggleConnection.setBackgroundColor(Color.RED)
        binding.toggleConnection.isEnabled = true
        binding.toggleConnection.setTextColor(Color.WHITE)
    }

    private fun connected() {
        binding.toggleConnection.setBackgroundColor(Color.GREEN)
        binding.toggleConnection.isEnabled = false
        binding.toggleConnection.text = "Connected"
        binding.toggleConnection.setTextColor(Color.WHITE)
    }

    private fun working(x: String) {
        binding.toggleConnection.setBackgroundColor(Color.YELLOW)
        binding.toggleConnection.isEnabled = false
        binding.toggleConnection.text = x
        binding.toggleConnection.setTextColor(Color.BLACK)
    }
}

private const val TAG = "BluetoothActivity2"