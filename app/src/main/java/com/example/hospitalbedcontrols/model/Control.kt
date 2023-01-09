package com.example.hospitalbedcontrols.model

import androidx.annotation.DrawableRes

data class Control(
    @DrawableRes val imageResourceId: Int,
    val name: String,
    val upBtn: ByteArray,
    val downBtn: ByteArray
    )