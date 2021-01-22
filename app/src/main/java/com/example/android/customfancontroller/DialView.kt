package com.example.android.customfancontroller

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.sin


//Enum to represent the available fan speeds

private enum class FanSpeed(val label: Int) {

    OFF(R.string.fan_off),
    LOW(R.string.fan_low),
    MEDIUM(R.string.fan_medium),
    HIGH(R.string.fan_high)

    //fxn to change the current fan speed to the next speed

    fun next () = when(this){

        OFF  -> LOW
        LOW -> MEDIUM
        HIGH -> OFF
    }
}

//constants for drawing dial indicators and labels

private const val RADIUS_OFFSET_LABEL = 30
private const val RADIUS_OFFSET_INDICATOR = -35
/*JvmOverloads annotation instructs the Kotlin compiler to generate
* overloads for this function that substitute default parameter values*/

//Add View constructor using @JVMOverloads
class DialView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(
        context, attrs, defStyleAttr) {
    /*initialize these values here instead of when a view is actuall drawn to ensure that the actual drawing
     step runs as fast as possible*/
    private var radius = 0.0f //current radius of the the circle
    private var fanSpeed = FanSpeed.OFF //The active selection, by default off

    //position variable which wll be used to draw label and indicator circle position on the screen

    private val pointPosition: PointF = PointF(0.0f, 0.0f)

    //initialize Paint Object with basic styles

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {

        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    //called when view first appears and whenever the view size changes

    /*Override onSizeChanged to colculate, positions, dimensions and any other values related to the custom
     view size instead of calculating them every time you draw*/
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        radius = (w.coerceAtMost(h) / 2.0 * 0.8).toFloat()
    }


    private fun PointF.computeXYForSpeed(pos: FanSpeed, radius: Float) {
        //Math.PI hold a constant value of 3.142

        //Angles are in radians
        val startAngle = Math.PI * (9 / 8.0) ///200 degrees/ 3.5 Radians


        //enum.ordinal returns the order of an enum instance
        val angle = startAngle + pos.ordinal * (Math.PI / 4)

        //width - width of the enclosing view, height - height of the enclosing view
        this.x = (radius * cos(angle)).toFloat() + width / 2
        this.y = (radius * sin(angle)).toFloat() + height / 2
    }

    //override onDraw() to render view on the screen

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //set color to gray if fan speed is off
        paint.color = if(fanSpeed == FanSpeed.OFF) Color.GRAY else Color.GRAY

        //draw a circle for the dial

        //drawCircle(float cx, float cy, float radius, Paint paint)

       /*the method uses the current view width and height to find the centre of the circle
        then takes a radius and pain to draw the circle*/


        canvas?.drawCircle((width/2).toFloat(), (height/2).toFloat(), radius, paint)

        /*width and height properties are members of the View Superclasss and indicate the current
                dimensions of the view*/


        //draw indicator circle
        val markerRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForSpeed(fanSpeed,markerRadius)
        paint.color = Color.BLACK

        canvas?.drawCircle(pointPosition.x, pointPosition.y, radius/12, paint)

//draw Text Labels
        val labelRadius = radius + RADIUS_OFFSET_LABEL
        for (i in FanSpeed.values()){

            pointPosition.computeXYForSpeed(i,labelRadius)
            val label = resources.getString(i.label)
            canvas?.drawText(label, pointPosition.x, pointPosition.y, paint)
        }

    }
}


