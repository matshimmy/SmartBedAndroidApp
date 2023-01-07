package com.example.hospitalbedcontrols

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hospitalbedcontrols.adapter.ControlAdapter
import com.example.hospitalbedcontrols.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var whichPage: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.controlsRecyclerView.adapter = ControlAdapter(
            applicationContext
        )

        binding.controlsRecyclerView.setHasFixedSize(true)
        supportActionBar?.hide()
        binding.avatar.setOnClickListener { launchSettings() }

//        binding.bottomNavigationView.setOnClickListener()
//        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.to_bluetooth -> {
                    launchBluetooth()
                    true
                }

                R.id.to_settings -> {
                    launchSettings()
                    true
                }

                else -> false
            }
        }
    }

    private fun launchBluetooth() {
        whichPage = Intent(this, BluetoothActivity::class.java)
        startActivity(whichPage)
    }

    private fun launchSettings() {
        whichPage = Intent(this, SettingsActivity::class.java)
        startActivity(whichPage)
    }



}