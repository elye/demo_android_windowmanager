package com.elyeproj.windowmanager

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.WindowManager

class FloatOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FloatView(context, attrs, defStyleAttr) {

    override val type: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        }
}
