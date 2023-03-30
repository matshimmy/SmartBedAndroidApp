package com.example.hospitalbedcontrols.data

import com.example.hospitalbedcontrols.R
import com.example.hospitalbedcontrols.model.Control


private val writeData = byteArrayOf(0x54, 0x65, 0x73, 0x74)// ascii Test

object ControlSource {
    val controls: List<Control> = listOf(
        Control(
            R.drawable.controlicon,
            "Head",
            byteArrayOf(0x02),
            byteArrayOf(0x04),
        ),

        Control(
            R.drawable.controlicon,
            "Foot",
            byteArrayOf(0x05),
            byteArrayOf(0x06),
        ),

        Control(
            R.drawable.controlicon,
            "Height",
            byteArrayOf(0x07),
            byteArrayOf(0x08),
        ),

        Control(
            R.drawable.controlicon,
            "Tilt",
            byteArrayOf(0x09),
            byteArrayOf(0x10),
        ),
    )
}