package com.andreibelous.animationsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.core.os.postDelayed
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dynamic1 = findViewById<DynamicView>(R.id.dynamic1)
        dynamic1.speed = DynamicView.Speed.HIGH

        val dynamic2 = findViewById<DynamicView>(R.id.dynamic2)
        dynamic2.speed = DynamicView.Speed.SLOW

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {

            button.isEnabled = false

            for (i in 0 until 200) {
                handler.postDelayed({
                    val ampl = Random.nextFloat() * 1200
                    dynamic1.setAmplitude(ampl)
                    dynamic2.setAmplitude(ampl)

                    if (i == 199) {
                        button.isEnabled = true
                    }
                }, i * 100L)
            }
        }
    }
}