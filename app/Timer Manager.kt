
package com.oneminute.signalai

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.oneminute.signalai.databinding.ActivityMainBinding
import com.oneminute.signalai.utils.TimerManager

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var timerManager: TimerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Lightweight Timer
        timerManager = TimerManager(
            onTick = { secondsLeft ->
                val formattedTime = String.format("00:%02d", secondsLeft)
                binding.tvTimer.text = formattedTime
            },
            onFinish = {
                binding.tvStatus.text = "● ANALYSIS COMPLETE"
                binding.tvStatus.setTextColor(android.graphics.Color.parseColor("#22C55E"))
                binding.btnStart.isEnabled = true
                binding.btnStart.text = "START ANALYSIS"
                
                // Show result container
                binding.cardResult.visibility = View.VISIBLE
                binding.tvResult.text = "Phase 1 Timer Ready!\nReady for Phase 2 Algorithm."
            }
        )

        binding.btnStart.setOnClickListener {
            if (!timerManager.isRunning) {
                binding.cardResult.visibility = View.GONE
                binding.tvStatus.text = "● ANALYZING..."
                binding.tvStatus.setTextColor(android.graphics.Color.parseColor("#EAB308"))
                binding.btnStart.isEnabled = false
                binding.btnStart.text = "ANALYZING ROUND..."
                
                timerManager.startTimer(60)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timerManager.stopTimer()
    }
}
