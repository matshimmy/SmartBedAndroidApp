package com.example.hospitalbedcontrols.speech

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.lifecycle.MutableLiveData

class SpeechListener(private val isListening: MutableLiveData<Boolean>, private val result: MutableLiveData<String>) : RecognitionListener {

    override fun onReadyForSpeech(params: Bundle) {
        Log.d(TAG, "onReadyForSpeech")
        isListening.value = true
    }

    override fun onBeginningOfSpeech() {
        Log.d(TAG, "onBeginningOfSpeech")
    }

    override fun onRmsChanged(rmsdB: Float) {
//        Log.d(TAG, "onRmsChanged $rmsdB")
    }

    override fun onBufferReceived(buffer: ByteArray) {
        Log.d(TAG, "onBufferReceived")
    }

    override fun onEndOfSpeech() {
        Log.d(TAG, "onEndofSpeech")
        isListening.value = false
    }

    override fun onError(error: Int) {
        Log.d(TAG, "error $error")
        isListening.value = false
    }

    override fun onResults(results: Bundle) {
        val data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!!
        val pattern = "(head|foot|height|tilt) (up|down) \\d seconds".toRegex(RegexOption.IGNORE_CASE)
        data.forEach {
            if (pattern.containsMatchIn(it)) {
                result.value = it
                Log.d(TAG, it)
                return@forEach
            }
        }

//        //debug
//        val pattern = Regex("testing up \\d Seconds")
//        data.forEach {
//            val match = pattern.find(it)
//            if (match != null) {
//                Log.d(TAG, match.value)
//            }
//        }
//        val word = data.first()
//        Log.d(TAG, "Results: $word ${data.contains("testing 1 2 3")} ${data.size}")
    }

    override fun onPartialResults(partialResults: Bundle) {
        Log.d(TAG, "onPartialResults")
    }

    override fun onEvent(eventType: Int, params: Bundle) {
        Log.d(TAG, "onEvent $eventType")
    }
}

const val TAG = "speechListener"