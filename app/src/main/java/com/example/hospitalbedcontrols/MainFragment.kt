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
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.example.hospitalbedcontrols.adapter.ControlAdapter
import com.example.hospitalbedcontrols.databinding.FragmentMainBinding
import com.example.hospitalbedcontrols.speech.SpeechListener
import com.example.hospitalbedcontrols.speech.SpeechPermissions


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private lateinit var micPermission: SpeechPermissions
    private val isListening = MutableLiveData(false)
    private val micResult = MutableLiveData("")


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
        micPermission = SpeechPermissions(requireContext())

        binding.controlsRecyclerView.adapter = ControlAdapter(context, micResult)

        binding.controlsRecyclerView.setHasFixedSize(true)

        attachMic()

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

    private fun attachMic() {
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        speechRecognizer.setRecognitionListener(SpeechListener(isListening, micResult))
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")

        isListening.observe(viewLifecycleOwner) {
            when(it) {
                true -> binding.micButton.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
                false -> binding.micButton.backgroundTintList = ColorStateList.valueOf(Color.RED)
            }
        }
        binding.micButton.setOnClickListener {
            if (!micPermission.checkPermissions())
                return@setOnClickListener
            if (isListening.value == true) {
                speechRecognizer.stopListening()
                binding.micButton.backgroundTintList = ColorStateList.valueOf(Color.RED)
            } else {
                speechRecognizer.startListening(intent)
                binding.micButton.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
            }
        }

    }

}