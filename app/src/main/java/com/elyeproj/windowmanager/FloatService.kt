package com.elyeproj.windowmanager

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.view.LayoutInflater

class FloatService: Service() {

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
        (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.view_service_float, null)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}