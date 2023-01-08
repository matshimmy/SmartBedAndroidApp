package com.example.hospitalbedcontrols

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import com.example.hospitalbedcontrols.adapter.ControlAdapter
import com.example.hospitalbedcontrols.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.controlsRecyclerView.adapter = ControlAdapter(context)

        binding.controlsRecyclerView.setHasFixedSize(true)
        binding.avatar.setOnClickListener { Navigation.findNavController(view).navigate(R.id.navToSettingsFragment) }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.to_bluetooth -> {
                    Navigation.findNavController(view).navigate(R.id.navToBluetoothFragment)
                    true
                }

                R.id.to_settings -> {
                    Navigation.findNavController(view).navigate(R.id.navToSettingsFragment)
                    true
                }

                else -> false
            }
        }
    }

}