package com.example.android.customfancontroller

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View


//Enum to represent the available fan speeds

private enum class FanSpeed(val label: Int) {

    OFF(R.string.fan_off),
    LOW(R.string.fan_low),
    MEDIUM(R.string.fan_medium),
    HIGH(R.string.fan_high)
}

//constants for drawing dial indicators and labels

private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35


//Add View constructor using @JVMOverloads
class DialView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(
        context, attrs, defStyleAttr) {

            private var radius = 0.0f //current radius of the the circle
    private var fanSpeed = FanSpeed.OFF //The active selection, by default off

    //position variable which wll be used to draw label and indicator circle position on the screen

    private val pointPosition: PointF = PointF(0.0f, 0.0f)

}