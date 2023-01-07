package com.example.hospitalbedcontrols.ble

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permissions (val context: Context) {

    private val isLocationPermissionGranted
        get() = context.hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)

    private val isBluetoothPermissionGranted
        @RequiresApi(Build.VERSION_CODES.S)
        get() = context.hasPermission(Manifest.permission.BLUETOOTH_CONNECT)

    private val isScanPermissionGranted
        @RequiresApi(Build.VERSION_CODES.S)
        get() = context.hasPermission(Manifest.permission.BLUETOOTH_SCAN)
    private var listOfPermissions: MutableSet<String> = mutableSetOf<String>()

    @RequiresApi(Build.VERSION_CODES.S)
    fun checkPermissions(): Boolean {
        var permissionsGranted = true

        listOfPermissions.clear()
        if (!isLocationPermissionGranted) {
            listOfPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (!isBluetoothPermissionGranted) {
            listOfPermissions.add(Manifest.permission.BLUETOOTH_CONNECT)
        }
        if (!isScanPermissionGranted) {
            listOfPermissions.add(Manifest.permission.BLUETOOTH_SCAN)
        }
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
                "To use bluetooth scanning LOCATION, BLUETOOTH and SCAN permissions must be granted."
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