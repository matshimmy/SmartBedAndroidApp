package com.example.hospitalbedcontrols

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.hospitalbedcontrols.adapter.ControlAdapter
import com.example.hospitalbedcontrols.databinding.FragmentMainBinding
import com.example.hospitalbedcontrols.speech.speechListener


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizer.setRecognitionListener(speechListener)

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
        binding.micButton.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1000)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Your Prompt")
            speechRecognizer.startListening(intent)
            binding.micButton.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
        }

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