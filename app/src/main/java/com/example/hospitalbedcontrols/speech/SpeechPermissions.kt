package com.example.hospitalbedcontrols.speech

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class SpeechPermissions(val context: Context) {

    private val isAudioPermissionGranted
        get() = context.hasPermission(Manifest.permission.RECORD_AUDIO)
    private var listOfPermissions: MutableSet<String> = mutableSetOf<String>()

    fun checkPermissions(): Boolean {
        var permissionsGranted = true

        listOfPermissions.clear()
        if (!isAudioPermissionGranted)
            listOfPermissions.add(Manifest.permission.RECORD_AUDIO)

        if (listOfPermissions.size > 0) {
            requestPermissionAlertDialog()
            permissionsGranted = false
        }

        return permissionsGranted
    }

    private fun requestPermissionAlertDialog() {
        AlertDialog.Builder(context)
            .setTitle("Permission required")
            .setMessage(
                "To use microphone RECORD_AUDIO permissions must be granted."
            )
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ ->
                sendPermissionsArray()
            }.create()
            .show()
    }

    private fun sendPermissionsArray() {
        ActivityCompat.requestPermissions(
            context as Activity,
            listOfPermissions.toTypedArray(),
            0
        )
    }

    // Extension functions
    private fun Context.hasPermission(permissionType: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permissionType) ==
                PackageManager.PERMISSION_GRANTED
    }
}