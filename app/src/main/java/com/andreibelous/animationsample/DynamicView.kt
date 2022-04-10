package com.andreibelous.animationsample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DynamicView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var lastUpdateTime: Long = System.currentTimeMillis()
    private var animateToAmplitude = 0f
    private var amplitude = 0f
    private var deltaAmplitude = 0f
    private var maxRadius = 0f
    private var minRadius = 0f
    private val paint = Paint().apply {
        color = Color.argb(100, 255, 0, 0)
    }

    var speed = Speed.HIGH

    init {
        setWillNotDraw(false)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        maxRadius = minOf(width / 2f, height / 2f) * 0.9f
        minRadius = maxRadius * 0.1f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val delta = System.currentTimeMillis() - lastUpdateTime
        calculateNextFrame(delta)

        val radius = minRadius + (maxRadius - minRadius) * amplitude / MAX_AMPLITUDE
        canvas.drawCircle(width / 2f, height / 2f, radius, paint)

        lastUpdateTime = System.currentTimeMillis()
        invalidate()
    }

    private fun calculateNextFrame(dt: Long) {
        if (animateToAmplitude != amplitude) {
            amplitude += deltaAmplitude * dt
            if (deltaAmplitude > 0) {
                amplitude = amplitude.coerceAtMost(animateToAmplitude)
            } else {
                amplitude = amplitude.coerceAtLeast(animateToAmplitude)
            }
        }
    }

    fun setAmplitude(value: Float) {
        animateToAmplitude = value
        val diff = animateToAmplitude - amplitude // current amplitude stored in view
        if (animateToAmplitude > amplitude) {
            deltaAmplitude = diff / (100f + 600f * speed.coef)
        } else {
            deltaAmplitude = diff / (100f + 1000f * speed.coef)
        }
    }

    enum class Speed(val coef: Float) {
        HIGH(0.35f),
        SLOW(0.50f)
    }

    private companion object {

        private const val MAX_AMPLITUDE = 1200f
    }
}