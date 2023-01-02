package com.example.hospitalbedcontrols

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hospitalbedcontrols.databinding.ActivityBluetoothBinding
import com.example.hospitalbedcontrols.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth

class BluetoothActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBluetoothBinding
    private lateinit var whichPage: Intent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBluetoothBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_bluetooth)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.to_home -> {
                    launchHome()
                    true
                }

                else -> false
            }
        }
    }


    private fun launchHome() {
        whichPage = Intent(this, MainActivity::class.java)
        startActivity(whichPage)
    }
}