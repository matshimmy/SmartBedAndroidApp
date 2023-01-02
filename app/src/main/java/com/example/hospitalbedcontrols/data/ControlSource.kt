package com.example.hospitalbedcontrols.data

import com.example.hospitalbedcontrols.R
import com.example.hospitalbedcontrols.model.Control


object ControlSource {
    val controls: List<Control> = listOf(
        Control(
            R.drawable.controlicon,
            "Head Control"

        ),

        Control(
            R.drawable.controlicon,
            "Foot Control"

        ),

        Control(
            R.drawable.controlicon,
            "Bed Control"

        ),

        Control(
            R.drawable.controlicon,
            "Trend Control"

        ),

        Control(
            R.drawable.controlicon,
            "Chair Control"

        ),
    )
}