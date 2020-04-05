package com.elyeproj.windowmanager

import android.content.Context
import android.util.AttributeSet
import android.view.WindowManager

class FloatPanelView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FloatView(context, attrs, defStyleAttr) {

    override val type: Int
        get() = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
}
