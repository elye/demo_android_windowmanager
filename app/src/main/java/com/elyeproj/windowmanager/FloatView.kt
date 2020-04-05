package com.elyeproj.windowmanager

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatImageView

abstract class FloatView (
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    protected val windowManager by lazy {
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }
    private var xInitCord: Int = 0
    private var yInitCord: Int = 0
    private var xInitMargin: Int = 0
    private var yInitMargin: Int = 0

    private val type: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

    init {
        val paramFloat = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)
        paramFloat.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL

        windowManager.addView(this, paramFloat)

        setOnTouchListener { _, event ->
            val layoutParams = layoutParams as WindowManager.LayoutParams

            val xCord = event.rawX.toInt()
            val yCord = event.rawY.toInt()
            val xCordDestination: Int
            val yCordDestination: Int

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    xInitCord = xCord
                    yInitCord = yCord
                    xInitMargin = layoutParams.x
                    yInitMargin = layoutParams.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val xDiffMove: Int = xCord - xInitCord
                    val yDiffMove: Int = yCord - yInitCord
                    xCordDestination = xInitMargin + xDiffMove
                    yCordDestination = yInitMargin + yDiffMove

                    layoutParams.x = xCordDestination
                    layoutParams.y = yCordDestination
                    windowManager.updateViewLayout(this, layoutParams)
                }
                MotionEvent.ACTION_UP -> { }
            }
            return@setOnTouchListener true
        }
    }
}
