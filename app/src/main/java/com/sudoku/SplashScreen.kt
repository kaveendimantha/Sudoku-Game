package com.sudoku

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var loadingBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        loadingBar = findViewById(R.id.loading)

        val startProgress = 0
        val endProgress = 100
        val duration = 2500L // total animation duration in milliseconds

        // Animate progress bar and navigate to MainActivity after animation
        progressWithAnimation(startProgress, endProgress, duration)
    }

    private fun progressWithAnimation(startProgress: Int, endProgress: Int, duration: Long) {
        var currentProgress = startProgress
        val step = 1
        val totalTime = duration / (endProgress - startProgress)

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                loadingBar.progress = currentProgress
                currentProgress += step
                if (currentProgress <= endProgress) {
                    handler.postDelayed(this, totalTime)
                } else {
                    val intent = Intent(this@SplashScreen, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, totalTime)
    }
}
