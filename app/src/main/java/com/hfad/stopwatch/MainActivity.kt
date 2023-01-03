package com.hfad.stopwatch

import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import com.hfad.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // lateinit var stopwatch: Chronometer  // the stop watch
    var running = false    // is the stop watch running?
    var offset: Long = 0   // the base offset for the stopwatch
    // Add key strings for use with the bundle.
    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    val BASE_KEY = "base"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)
        // Get a reference for the stopwatch
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // stopwatch = findViewById(R.id.stopwatch)

        // Restore the previous state.
        if (savedInstanceState != null) {
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if (running) {
                binding.stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                binding.stopwatch.start()
            }else setBaseTime()
        }
        // what the app does when another activity makes it invisible.
        fun onPause() {
            super.onPause()
            if (running) {
                saveOffset()
                binding.stopwatch.stop()
            }
        }

        fun onResume() {
            super.onResume()
            if (running) {
                setBaseTime()
                binding.stopwatch.start()
                offset = 0
            }
        }
        // The button starts the stopwatch if its not running
        // val startButton = findViewById<Button>(R.id.start_button)
        // We are trying to use binding view to make our code more efficient.
        binding.startButton.setOnClickListener {
            if (!running) {
                setBaseTime()
                binding.stopwatch.start()
                running = true
            }
        }
        // The pause button pauses the stopwatch if its running
        // val pauseButton = findViewById<Button>(R.id.pause_button)
        binding.pauseButton.setOnClickListener {
            if (running) {
                saveOffset()
                binding.stopwatch.stop()
                running = false
            }
        }
        // The reset button set the offset and stopwatch to zero
        // val resetButton = findViewById<Button>(R.id.reset_button)
        binding.resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }
    // .
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY, offset)
        savedInstanceState.putBoolean(RUNNING_KEY, running)
        savedInstanceState.putLong(BASE_KEY, binding.stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }
    // Update te stopwatch base time, allowing for any offset
    fun setBaseTime() {
        binding.stopwatch.base = SystemClock.elapsedRealtime() - offset
    }
    // Record the offset
    fun saveOffset() {
        offset = SystemClock.elapsedRealtime() - binding.stopwatch.base
    }
}