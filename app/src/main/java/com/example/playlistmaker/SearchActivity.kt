package com.example.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@Suppress("DEPRECATION")
class SearchActivity : AppCompatActivity() {

    private lateinit var searchRequest: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        val toolbar = findViewById<Toolbar>(R.id.search_toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val inputEditText = findViewById<EditText>(R.id.search_input)
        if (savedInstanceState != null) {
            inputEditText.setText(savedInstanceState.getString(SEARCH_REQUEST, ""))
        }

        val clearButton = findViewById<ImageView>(R.id.clearButton)
        clearButton.setOnClickListener {
            inputEditText.setText("")
            inputEditText.clearFocus()
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
        }

        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearButton.visibility = clearButtonVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
                searchRequest = s.toString()
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)
        val trackName1 = getString(R.string.trackName1)
        val trackName2 = getString(R.string.trackName2)
        val trackName3 = getString(R.string.trackName3)
        val trackName4 = getString(R.string.trackName4)
        val trackName5 = getString(R.string.trackName5)

        val artistName1 = getString(R.string.artistName1)
        val artistName2 = getString(R.string.artistName2)
        val artistName3 = getString(R.string.artistName3)
        val artistName4 = getString(R.string.artistName4)
        val artistName5 = getString(R.string.artistName5)

        val trackTime1 = getString(R.string.trackTime1)
        val trackTime2 = getString(R.string.trackTime2)
        val trackTime3 = getString(R.string.trackTime3)
        val trackTime4 = getString(R.string.trackTime4)
        val trackTime5 = getString(R.string.trackTime5)

        val artworkUrl1 = getString(R.string.artworkUrl1)
        val artworkUrl2 = getString(R.string.artworkUrl2)
        val artworkUrl3 = getString(R.string.artworkUrl3)
        val artworkUrl4 = getString(R.string.artworkUrl4)
        val artworkUrl5 = getString(R.string.artworkUrl5)
        val track1 = Track(
            trackName1, artistName1, trackTime1, artworkUrl1
        )
        val track2 = Track(
            trackName2, artistName2, trackTime2, artworkUrl2
        )
        val track3 = Track(
            trackName3, artistName3, trackTime3, artworkUrl3
        )
        val track4 = Track(
            trackName4, artistName4, trackTime4, artworkUrl4
        )
        val track5 = Track(
            trackName5, artistName5, trackTime5, artworkUrl5
        )
        val arrayOfTrack = SearchResultAdapter(arrayListOf(track1, track2, track3, track4, track5))

        val rvTrack = findViewById<RecyclerView>(R.id.recyclerView)
        rvTrack.adapter = arrayOfTrack

        rvTrack.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_REQUEST, searchRequest)
    }

    private fun clearButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    companion object {
        private const val SEARCH_REQUEST = "SEARCH_REQUEST"
    }

}
