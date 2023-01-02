package com.example.hospitalbedcontrols

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hospitalbedcontrols.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var whichPage: Intent
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.LogOut.setOnClickListener {
            firebaseAuth.signOut()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


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