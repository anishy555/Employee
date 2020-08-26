package com.ajm.employee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar

class SplashActivity : AppCompatActivity() {

    var progressBar : ProgressBar? = null
    var SPLASH_TIME_OUT: Long = 100
    var progress_value = 0
    var progress_secondary_value = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        progressBar = findViewById(R.id.splash_progress) as ProgressBar
        progressUpdater()
    }

    fun progressUpdater()
    {
        Handler().apply {

            val runnable =object : Runnable
            {
                override fun run() {

                    progress_value = progress_value + 2
                    progress_secondary_value = progress_secondary_value+4
                    progressBar?.progress = progress_value
                    progressBar?.secondaryProgress = progress_secondary_value
                    if (progress_value >= 100) {
                        val intent =
                            Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        progressUpdater()
                    }
                }
            }

            postDelayed(runnable, SPLASH_TIME_OUT)

        }
    }
}
