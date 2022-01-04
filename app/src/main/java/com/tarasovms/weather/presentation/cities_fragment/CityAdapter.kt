package com.tarasovms.weather.presentation.cities_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tarasovms.weather.R
import com.tarasovms.weather.data.remote.WeatherResponse

class CityAdapter (private var items: List<WeatherResponse>, private val onCityClickListener: OnCityClickListener) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cityRecycleView = items[position]
        holder.name.text = cityRecycleView.cityName
        holder.temp.text = cityRecycleView.main.temp.toString()+"°C"
        holder.id.text = position.toString()
        holder.bindView(cityRecycleView, onCityClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_info_city, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(view: View ) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.TView_recycler_id)
        val name: TextView = view.findViewById(R.id.TView_recycler_name_city)
        val temp: TextView = view.findViewById(R.id.TView_recycler_temperature_city)

        fun bindView(weatherResponse: WeatherResponse,onCityClickListener: OnCityClickListener){
//            name.setOnClickListener {
//                onCityClickListener.clickedCityItem(weatherResponse)
//            }
        }
    }

    fun updateItems(items: List<WeatherResponse>) {
        this.items = items
        notifyDataSetChanged()
    }

}







