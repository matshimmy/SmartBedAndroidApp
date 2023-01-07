package com.example.hospitalbedcontrols

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.hospitalbedcontrols.databinding.ActivityBluetoothBinding
import com.example.hospitalbedcontrols.model.BluetoothViewModel

class BluetoothActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBluetoothBinding
    private lateinit var viewModel: BluetoothViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBluetoothBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        viewModel = ViewModelProvider(this).get(BluetoothViewModel::class.java)
        viewModel.connectionStatus.observe(this) { isConnected ->
            if (isConnected) {
                binding.toggleConnection.text = "Connected"
                binding.toggleConnection.setBackgroundColor(Color.GREEN)
                binding.toggleConnection.isEnabled = false
            } else {
                binding.toggleConnection.text = "Connect"
                binding.toggleConnection.setBackgroundColor(Color.RED)
                binding.toggleConnection.isEnabled = true
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
            viewModel.connectToBleDevice()
        }
    }
}
