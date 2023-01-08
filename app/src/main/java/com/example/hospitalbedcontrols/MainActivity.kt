package com.example.hospitalbedcontrols

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.hospitalbedcontrols.ble.isBLEok
import com.example.hospitalbedcontrols.model.BluetoothViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BluetoothViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        viewModel = ViewModelProvider(this).get(BluetoothViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun finish() {
        super.finish()
        if (isBLEok(this, viewModel))
            viewModel.disconnectBleDevice()
    }
}