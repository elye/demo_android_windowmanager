package com.elyeproj.windowmanager

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatImageView

class FloatActivityView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FloatView(context, attrs, defStyleAttr) {

    override val type: Int
        get() = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
}
