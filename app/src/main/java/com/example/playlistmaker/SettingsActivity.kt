package com.example.playlistmaker

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity: AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        val backButton = findViewById<ImageView>(R.id.arrow_back)

        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}