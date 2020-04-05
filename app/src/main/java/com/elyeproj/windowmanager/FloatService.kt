package com.elyeproj.windowmanager

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.RelativeLayout

class FloatService: Service() {

    private val windowManager by lazy {
        getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    private var xInitCord: Int = 0
    private var yInitCord: Int = 0
    private var xInitMargin: Int = 0
    private var yInitMargin: Int = 0

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        return if (startId == START_STICKY) {
            handleStart()
            super.onStartCommand(intent, flags, startId)
        } else {
            START_NOT_STICKY
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleStart() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val floatView = inflater.inflate(R.layout.view_float, null) as RelativeLayout

        val paramFloat = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_PHONE
            },
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT)
        paramFloat.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL

        windowManager.addView(floatView, paramFloat)

        floatView.setOnTouchListener { _, event ->
            val layoutParams = floatView.layoutParams as WindowManager.LayoutParams

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
                    windowManager.updateViewLayout(floatView, layoutParams)
                }
                MotionEvent.ACTION_UP -> { }
            }
            return@setOnTouchListener true
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}