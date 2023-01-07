package com.example.hospitalbedcontrols

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.hospitalbedcontrols.databinding.ActivityBluetoothBinding
import com.example.hospitalbedcontrols.model.BluetoothViewModel

class BluetoothActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBluetoothBinding
    //val viewModel = ViewModelProvider(this)[BluetoothViewModel::class.java]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBluetoothBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

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
            if (isBTConnected()) {
                binding.toggleConnection.text = "Connected"
                binding.toggleConnection.setBackgroundColor(Color.GREEN)
            }
        }
    }

    private fun isBTConnected(): Boolean {
        return true
    }
}
