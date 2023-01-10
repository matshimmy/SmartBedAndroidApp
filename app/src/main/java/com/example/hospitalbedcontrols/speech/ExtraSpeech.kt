package com.example.hospitalbedcontrols.speech

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log

val speechListener = object : RecognitionListener {
    override fun onReadyForSpeech(params: Bundle?) {
        // called when the recognizer is ready for the user to start speaking
    }

    override fun onBeginningOfSpeech() {
        // called when the user starts speaking
    }

    override fun onRmsChanged(rmsdB: Float) {
        // called when the sound level in the audio stream changes
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        // called when a sound buffer is received
    }

    override fun onEndOfSpeech() {
        // called when the user stops speaking
    }

    override fun onError(error: Int) {
        // called when an error occurs
    }

    override fun onResults(results: Bundle?) {
        val data = results!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val word = data?.get(data.size - 1) as String

        Log.d(TAG, "partial_results: $word")
    }

    override fun onPartialResults(partialResults: Bundle?) {
        // called when partial speech recognition results are available
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        // called for miscellaneous events
    }
}

const val TAG = "speechListener"