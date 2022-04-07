package com.andreibelous.animationsample

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
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
    private var animateAmplitudeDiff = 0f
    private var maxRadius = 0f
    private val paint = TextPaint().apply {
        textSize = 80f
    }

    init {
        setWillNotDraw(false)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        maxRadius = minOf(width / 2f, height / 2f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val delta = System.currentTimeMillis() - lastUpdateTime
        doNextFrame(delta)

        canvas.drawText(amplitude.toString(), width / 2f, height / 2f, paint)

        lastUpdateTime = System.currentTimeMillis()
        invalidate()
    }

    private fun doNextFrame(dt: Long) {
        if (animateToAmplitude != amplitude) {
            amplitude += animateAmplitudeDiff * dt
            if (animateAmplitudeDiff > 0) {
                if (amplitude > animateToAmplitude) {
                    amplitude = animateToAmplitude
                }
            } else {
                if (amplitude < animateToAmplitude) {
                    amplitude = animateToAmplitude
                }
            }
        }
    }

    fun updateAmplitude(amplitude: Float) {
        animateToAmplitude = amplitude
        if (animateToAmplitude > amplitude) {
            animateAmplitudeDiff =
                (animateToAmplitude - amplitude) / (100f + 300f * ANIMATION_SPEED_WAVE_HUGE)
        } else {
            animateAmplitudeDiff =
                (animateToAmplitude - amplitude) / (100f + 500f * ANIMATION_SPEED_WAVE_HUGE)
        }
    }

    private companion object {

        private const val ANIMATION_SPEED_WAVE_HUGE = 0.35f
    }
}