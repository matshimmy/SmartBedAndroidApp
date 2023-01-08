package com.example.hospitalbedcontrols.data

import com.example.hospitalbedcontrols.R
import com.example.hospitalbedcontrols.model.Control


object ControlSource {
    val controls: List<Control> = listOf(
        Control(
            R.drawable.controlicon,
            "Head"
        ),

        Control(
            R.drawable.controlicon,
            "Foot"
        ),

        Control(
            R.drawable.controlicon,
            "Height"
        ),

        Control(
            R.drawable.controlicon,
            "Tilt"
        ),
    )
}