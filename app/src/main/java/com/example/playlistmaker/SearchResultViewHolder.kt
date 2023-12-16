package com.example.playlistmaker

import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class SearchResultViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val artistNameView = itemView.findViewById<TextView>(R.id.track_list_artist_name)
    private val trackNameView = itemView.findViewById<TextView>(R.id.track_list_name)
    private val trackTimeView = itemView.findViewById<TextView>(R.id.track_list_time)
    private val artworkUrlView = itemView.findViewById<ImageView>(R.id.track_list_image_url)

    fun bind(item: Track) {
        trackNameView.text = item.trackName
        artistNameView.text = item.artistName
        trackTimeView.text = item.trackTime
        Glide.with(itemView)
            .load(item.artworkUrl100)
            .centerCrop()
            .transform(RoundedCorners(dpToPx(itemView, 2f)))
            .placeholder(R.drawable.ic_placeholder)
            .into(artworkUrlView)
    }
}

private fun dpToPx(view: View, dp: Float): Int {
    val displayMetrics = view.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics).toInt()
}
