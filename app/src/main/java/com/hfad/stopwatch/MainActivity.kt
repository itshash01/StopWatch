package com.hfad.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer

class MainActivity : AppCompatActivity() {
    lateinit var stopwatch: Chronometer  // the stop watch
    var running = false    // is the stop watch running?
    var offset: Long = 0   // the base offset for the stopwatch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get a reference for the stopwatch
        stopwatch = findViewById<Chronometer>(R.id.stopwatch)
        // The button starts the stopwatch i its not running
        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener {
            if (!running) {
                setBaseTime()
                stopwatch.start()
                running = true
            }
        }

        // The pause button pauses the stopwatch if its running
        val pauseButton = findViewById<Button>(R.id.pause_button)
        pauseButton.setOnClickListener {
            if (running) {
                saveOffset()
                stopwatch.stop()
                running = false
            }
        }

        // The reset button set the offset and stopwatch to zero
        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }
    // Update te stopwatch base time, allowing for any offset
    fun setBaseTime() {
        stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    // Record the offset
    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - stopwatch.base
    }
}