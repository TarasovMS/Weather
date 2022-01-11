package com.tarasovms.weather.presentation.cities_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tarasovms.weather.R
import com.tarasovms.weather.base.Constants.ONE
import com.tarasovms.weather.data.remote.WeatherResponse
import kotlin.math.roundToInt


class CityAdapter(private var items: List<WeatherResponse>,
                  private val onCityClickListener: OnCityClickListener) : RecyclerView.Adapter<CityAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cityRecycleView = items[position]

        var weatherConditions = ""
        cityRecycleView.weather.map { weatherConditions = it.description }

        holder.id.text = position.plus(ONE).toString()
        holder.name.text = cityRecycleView.cityName
        holder.temp.text = "  - Температура = ${cityRecycleView.main.temp.roundToInt()} °C \n"
        holder.humidity.text = "  - Влажность = ${cityRecycleView.main.humidity}% \n"
        holder.windSpeed.text = "  - Ветер = ${cityRecycleView.wind.speed}м/с"
        holder.weatherConditions.text = "  - Погодные условия = $weatherConditions"
        holder.ll.visibility = if (cityRecycleView.expandable) View.VISIBLE else View.GONE
        holder.bindView(cityRecycleView, onCityClickListener, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_info_city, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(private val view: View ) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.tv_recycler_id)
        val name: TextView = view.findViewById(R.id.tv_recycler_name_city)
        val temp: TextView = view.findViewById(R.id.tv_recycler_temp)
        val humidity: TextView = view.findViewById(R.id.tv_recycler_humidity)
        val windSpeed: TextView = view.findViewById(R.id.tv_recycler_wind_speed)
        val weatherConditions: TextView = view.findViewById(R.id.tv_recycler_weather_conditions)
        val ll: LinearLayout = view.findViewById(R.id.ll_recyclerView_weather)

        fun bindView(weatherResponse: WeatherResponse,onCityClickListener: OnCityClickListener, position: Int){
            view.setOnClickListener {
                onCityClickListener.clickedCityItem(weatherResponse, position)
            }
        }
    }

    fun updateItems(items: List<WeatherResponse>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun updateItems(position: Int) {
        notifyItemChanged(position)
    }
}







