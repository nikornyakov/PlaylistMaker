package com.example.playlistmaker

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private var searchRequest: String = ""
    private val itunesBaseUrl = "https://itunes.apple.com"
    private val retrofit =
        Retrofit.Builder().baseUrl(itunesBaseUrl).addConverterFactory(GsonConverterFactory.create())
            .build()
    private val itunesService = retrofit.create(ITunesApi::class.java)
    private val searchResultsAdapter = SearchResultAdapter(songs)


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search)

        val inputText = findViewById<TextInputLayout>(R.id.search_input)
        val inputEditText = findViewById<TextInputEditText>(R.id.search_clear_button)
        val notFoundPlaceholder = findViewById<FrameLayout>(R.id.search_include_not_found)
        val noConnectionPlaceholder = findViewById<FrameLayout>(R.id.search_include_no_connect)
        val searchResultsRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        searchResultsRecyclerView.adapter = searchResultsAdapter

        if (savedInstanceState != null) {
            inputEditText.setText(savedInstanceState.getString(SEARCH_REQUEST, ""))
        }

        val toolbar = findViewById<Toolbar>(R.id.search_toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        val refreshButton = findViewById<Button>(R.id.button_update)
        refreshButton.setOnClickListener {
            notFoundPlaceholder!!.visibility = View.GONE
            noConnectionPlaceholder!!.visibility = View.GONE
            search(searchRequest)
        }


        inputText.setEndIconOnClickListener {
            inputEditText.text?.clear()
            inputEditText.clearFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(inputEditText.windowToken, 0)
            songs.clear()
            searchResultsAdapter.notifyDataSetChanged()
        }
        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                notFoundPlaceholder.visibility = View.GONE
                noConnectionPlaceholder.visibility = View.GONE
                search(searchRequest)
            }
            false
        }


        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchRequest = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                // empty
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)


    }

    private fun search(queryInput: String) {
        itunesService.findTrack(queryInput).enqueue(object : Callback<TrackResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<TrackResponse>, response: Response<TrackResponse>
            ) {
                val notFoundPlaceholder = findViewById<FrameLayout>(R.id.search_include_not_found)
                if (response.body()?.resultCount == 0) {
                    notFoundPlaceholder.visibility = View.VISIBLE
                } else {
                    songs.clear()
                    songs.addAll(response.body()?.results!!)
                    searchResultsAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                val noConnectionPlaceholder =
                    findViewById<FrameLayout>(R.id.search_include_no_connect)
                noConnectionPlaceholder.visibility = View.VISIBLE
            }

        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SEARCH_REQUEST, searchRequest)
    }


    companion object {
        private const val SEARCH_REQUEST = "SEARCH_REQUEST"
    }

}
