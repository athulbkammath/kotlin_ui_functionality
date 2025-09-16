package com.eto.binding_usage_project.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ScannerOverlayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = resources.displayMetrics.density * 3f // ~3dp
    }

    private val maskPaint = Paint().apply {
        color = 0x80000000.toInt() // semi-transparent black
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val w = width.toFloat()
        val h = height.toFloat()

        // centered scanning box size (change multipliers as you like)
        val boxWidth = w * 0.7f
        val boxHeight = h * 0.3f
        val left = (w - boxWidth) / 2f
        val top = (h - boxHeight) / 2f
        val right = left + boxWidth
        val bottom = top + boxHeight

        // draw mask outside the box
        canvas.drawRect(0f, 0f, w, top, maskPaint)
        canvas.drawRect(0f, bottom, w, h, maskPaint)
        canvas.drawRect(0f, top, left, bottom, maskPaint)
        canvas.drawRect(right, top, w, bottom, maskPaint)

        // draw box border
        canvas.drawRect(left, top, right, bottom, borderPaint)
    }
}