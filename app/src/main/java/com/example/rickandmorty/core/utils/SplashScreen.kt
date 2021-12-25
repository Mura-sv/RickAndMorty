package com.example.rickandmorty.core.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.rickandmorty.MainActivity

class SplashScreen : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}