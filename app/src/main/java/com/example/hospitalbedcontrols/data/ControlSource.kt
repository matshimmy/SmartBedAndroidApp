package com.example.hospitalbedcontrols.data

import com.example.hospitalbedcontrols.R
import com.example.hospitalbedcontrols.model.Control


private val writeData = byteArrayOf(0x54, 0x65, 0x73, 0x74)// ascii Test

object ControlSource {
    val controls: List<Control> = listOf(
        Control(
            R.drawable.controlicon,
            "Head",
            writeData,
            writeData
        ),

        Control(
            R.drawable.controlicon,
            "Foot",
            writeData,
            writeData
        ),

        Control(
            R.drawable.controlicon,
            "Height",
            writeData,
            writeData
        ),

        Control(
            R.drawable.controlicon,
            "Tilt",
            writeData,
            writeData
        ),
    )
}