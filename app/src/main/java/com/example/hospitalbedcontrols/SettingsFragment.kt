package com.example.hospitalbedcontrols

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import com.example.hospitalbedcontrols.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(view)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.LogOut.setOnClickListener {
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