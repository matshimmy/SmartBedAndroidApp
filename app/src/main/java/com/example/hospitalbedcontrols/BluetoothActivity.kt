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

        binding.connectBT.setOnClickListener {
            if (isBLEok(this, viewModel))
                viewModel.connectBleDevice()
        }
        binding.disconnectBT.setOnClickListener {
            if (isBLEok(this, viewModel))
                viewModel.disconnectBleDevice()
        }
    }

    private fun allowScan() {
        if (viewModel.connectionStatus.value is ViewState.Connected) return connected()
        binding.connectBT.text = "Connect"
        binding.connectBT.setBackgroundColor(Color.RED)
        binding.connectBT.isEnabled = true
        binding.connectBT.setTextColor(Color.WHITE)
        binding.disconnectBT.isEnabled = false
    }

    private fun connected() {
        binding.connectBT.setBackgroundColor(Color.GREEN)
        binding.connectBT.isEnabled = false
        binding.connectBT.text = "Connected"
        binding.connectBT.setTextColor(Color.WHITE)
        binding.disconnectBT.isEnabled = true
    }

    private fun working(x: String) {
        binding.connectBT.setBackgroundColor(Color.YELLOW)
        binding.connectBT.isEnabled = false
        binding.connectBT.text = x
        binding.connectBT.setTextColor(Color.BLACK)
        binding.disconnectBT.isEnabled = false
    }
}

private const val TAG = "BluetoothActivity2"