package com.tarasovms.weather.presentation.map_fragment

import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.tarasovms.weather.R

class CustomInfoWindow(private val view: View): GoogleMap.InfoWindowAdapter {

    private fun windowText(marker: Marker, view: View){
        val title = marker.title
        val snippet = marker.snippet
        val tvSnippet = view.findViewById<TextView>(R.id.tv_customView_snippet)
        val tvTitle = view.findViewById<TextView>(R.id.tv_customView_title)

        if (!title.equals("")&& !snippet.equals("")) {
            tvTitle.text = title
            tvSnippet.text = snippet
        }
    }

    override fun getInfoContents(p0: Marker): View {
        windowText(p0,view)
        return view
    }

    override fun getInfoWindow(p0: Marker): View? {
        return null
    }
}