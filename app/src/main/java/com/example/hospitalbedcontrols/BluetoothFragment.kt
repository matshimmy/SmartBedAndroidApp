package com.example.hospitalbedcontrols

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.hospitalbedcontrols.ble.ScanStatus
import com.example.hospitalbedcontrols.ble.ViewState
import com.example.hospitalbedcontrols.ble.isBLEok
import com.example.hospitalbedcontrols.ble.label
import com.example.hospitalbedcontrols.databinding.FragmentBluetoothBinding
import com.example.hospitalbedcontrols.model.BluetoothViewModel


class BluetoothFragment : Fragment(R.layout.fragment_bluetooth) {

    private lateinit var binding: FragmentBluetoothBinding
    private lateinit var viewModel: BluetoothViewModel

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBluetoothBinding.bind(view)

        viewModel = ViewModelProvider(activity as AppCompatActivity)[BluetoothViewModel::class.java]

        if (viewModel.connectionStatus.value is ViewState.Connected) connected()
        viewModel.connectionStatus.observe(viewLifecycleOwner) {
            when (it) {
                ViewState.Connected -> connected()
                ViewState.Disconnected -> allowScan()
                else -> working(viewModel.connectionStatus.value?.label as String)
            }
        }

        viewModel.scanStatus.observe(viewLifecycleOwner) {
            when (it) {
                is ScanStatus.Scanning -> working("Scanning...")
                else -> allowScan()
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.to_home -> {
                    Navigation.findNavController(view).navigate(R.id.navToMainFragment)
                    true
                }

                R.id.to_settings -> {
                    Navigation.findNavController(view).navigate(R.id.navToSettingsFragment)
                    true
                }

                else -> false
            }
        }

        binding.connectBT.setOnClickListener {
            if (isBLEok(activity as AppCompatActivity, viewModel))
                viewModel.connectBleDevice()
        }
        binding.disconnectBT.setOnClickListener {
            if (isBLEok(activity as AppCompatActivity, viewModel))
                viewModel.disconnectBleDevice()
        }
        binding.connectWifi.setOnClickListener {
            var ssid = String(byteArrayOf(0x00) + binding.ssidText.text.toString().toByteArray() + byteArrayOf(0x00))
            Log.d(TAG, ssid)
            var sspass = String(byteArrayOf(0x01) + binding.sspassText.text.toString().toByteArray() + byteArrayOf(0x01))
            Log.d(TAG, sspass)
            if (isBLEok(activity as AppCompatActivity, viewModel)) {
                if (binding.ssidText.text?.length!! > 0)
                    viewModel.writeBle(
                        byteArrayOf(0x00) + binding.ssidText.text.toString()
                            .toByteArray() + byteArrayOf(0x00)
                    )
                if (binding.sspassText.text?.length!! > 0)
                    viewModel.writeBle(
                        byteArrayOf(0x01) + binding.sspassText.text.toString()
                            .toByteArray() + byteArrayOf(0x01)
                    )
            }
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

private const val TAG = "BluetoothFragment"