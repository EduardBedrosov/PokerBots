package com.example.poker

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        try {
            this.supportActionBar?.hide()
        } // catch block to handle NullPointerException
        catch (e: Exception) {

        }
        setContentView(R.layout.activity_main)
    }
}