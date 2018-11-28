package me.toptas.kooldownsample

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
    }
}
