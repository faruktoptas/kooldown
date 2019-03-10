package me.toptas.kooldownsample

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import me.toptas.kooldown.OnProgressListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        kd.listener = object : OnProgressListener {
            override fun onComplete() {
                Log.v("asd", "completed")
            }
        }

        kd.setOnClickListener {
            kd.apply {
                if (isAnimationRunning) {
                    pause()
                } else {
                    start()
                }
            }
        }

        kd2.setOnClickListener {
            kd2.start()
        }

        kd2.listener = object : OnProgressListener {
            override fun onComplete() {
                Log.i("asd", "kd2 completed")
            }
        }

        kd3.apply {
            strokeWidth = 20f
            progress = 180
            autoStart = true
            startAngle = 180f
            inactiveColor = Color.LTGRAY
            duration = 2000
            activeColor = Color.BLUE
        }
    }
}
