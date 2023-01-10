package com.example.hospitalbedcontrols

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.hospitalbedcontrols.adapter.ControlAdapter
import com.example.hospitalbedcontrols.databinding.FragmentMainBinding


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Prevents finish() on activity and stopping connection, must logout
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Navigation.findNavController(view).navigateUp()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        binding = FragmentMainBinding.bind(view)
        binding.controlsRecyclerView.adapter = ControlAdapter(context)

        binding.controlsRecyclerView.setHasFixedSize(true)

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