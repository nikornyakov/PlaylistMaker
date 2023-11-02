package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val buttonSearch: View = findViewById(R.id.search_button)
        buttonSearch.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }

        val buttonMedia: View = findViewById(R.id.media_button)
        buttonMedia.setOnClickListener {
            val intent = Intent(this@MainActivity, MediaActivity::class.java)
            startActivity(intent)
        }

        val buttonSettings: View = findViewById(R.id.settings_button)
        buttonSettings.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}




