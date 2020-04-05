package com.elyeproj.windowmanager

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val SERVICE_FLOAT_REQUEST_CODE = 1000
        const val ACTIVITY_SERVICE_FLOAT_REQUEST_CODE = 2000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_service_float.setOnClickListener{
            if (requestPermission(SERVICE_FLOAT_REQUEST_CODE)) {
                return@setOnClickListener
            }
            showServiceFloat()
        }

        btn_activity_float.setOnClickListener {
            showActivityFloat()
        }

        btn_activity_service_float.setOnClickListener {
            if (requestPermission(ACTIVITY_SERVICE_FLOAT_REQUEST_CODE)) {
                return@setOnClickListener
            }
            showActivityServiceFlaot()
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SERVICE_FLOAT_REQUEST_CODE ||
                requestCode == ACTIVITY_SERVICE_FLOAT_REQUEST_CODE) {
            Handler().postDelayed({
                if (Settings.canDrawOverlays(this)) {
                    when (requestCode) {
                        SERVICE_FLOAT_REQUEST_CODE -> showServiceFloat()
                        ACTIVITY_SERVICE_FLOAT_REQUEST_CODE -> showActivityServiceFlaot()
                    }
                }
            }, 500)
        }
    }

    private fun showServiceFloat() {
        startService(Intent(this, ServiceFloat::class.java))
    }

    private fun showActivityFloat() {
        (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.view_activity_float, null)
    }

    private fun showActivityServiceFlaot() {
        (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.view_service_float, null)
    }

    private fun requestPermission(requestCode: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, requestCode)
                return true
            }
        }
        return false
    }
}
