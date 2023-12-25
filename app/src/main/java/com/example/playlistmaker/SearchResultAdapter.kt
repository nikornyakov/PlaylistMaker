package com.example.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SearchResultAdapter(
    private val arrayOfTrack: ArrayList<Track>
) : RecyclerView.Adapter<SearchResultViewHolder>() {
    fun addTracks(arrayOfTrack: ArrayList<Track>) {
        arrayOfTrack.addAll(arrayOfTrack)
        notifyItemRangeInserted(0, arrayOfTrack.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_list, parent, false)
        return SearchResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind(arrayOfTrack[position])
    }

    override fun getItemCount(): Int {
        return arrayOfTrack.size
    }

}
