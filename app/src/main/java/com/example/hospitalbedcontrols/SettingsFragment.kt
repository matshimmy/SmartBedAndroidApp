package com.example.hospitalbedcontrols

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.hospitalbedcontrols.ble.isBLEok
import com.example.hospitalbedcontrols.databinding.FragmentSettingsBinding
import com.example.hospitalbedcontrols.model.BluetoothViewModel
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var viewModel: BluetoothViewModel

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        firebaseAuth = FirebaseAuth.getInstance()

        viewModel = ViewModelProvider(activity as AppCompatActivity)[BluetoothViewModel::class.java]

        binding.LogOut.setOnClickListener {
            if (isBLEok(activity as AppCompatActivity, viewModel))
                viewModel.disconnectBleDevice()
            firebaseAuth.signOut()

            activity?.finish()
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.to_home -> {
                    Navigation.findNavController(view).navigate(R.id.navToMainFragment)
                    true
                }

                else -> false
            }
        }
    }
}