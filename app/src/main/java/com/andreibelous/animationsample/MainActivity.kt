package com.andreibelous.animationsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<DynamicView>(R.id.dynamic)
        view.postDelayed({ view.updateAmplitude(200f) }, 1000L)
    }
}