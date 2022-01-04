package com.tarasovms.weather.presentation.cities_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.model.LatLng
import com.tarasovms.weather.data.remote.Default
import com.tarasovms.weather.data.remote.WeatherResponse
import com.tarasovms.weather.databinding.ListCitiesFragmentBinding
import com.tarasovms.weather.presentation.App
import com.tarasovms.weather.presentation.CommonViewModel

class CitiesFragment: Fragment() {

    private lateinit var vm: CommonViewModel
    private lateinit var ui: ListCitiesFragmentBinding
    private lateinit var cityAdapter: CityAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val defaultCity = LatLng(Default.defaultCity.latitude, Default.defaultCity.longitude)
        val app = (context?.applicationContext as App)

        cityAdapter = CityAdapter(listOf(), object: OnCityClickListener{
            override fun clickedCityItem(weatherResponse: WeatherResponse) {
                Toast.makeText(context,"Нажат ${weatherResponse.cityName}", Toast.LENGTH_LONG).show()
            }
        })
        ui = ListCitiesFragmentBinding.inflate(layoutInflater)
        ui.recyclerViewCity.adapter = cityAdapter

        vm = CommonViewModel(app)
        if (vm.cityList.value != null) cityAdapter.updateItems(vm.cityList.value!!)
        else vm.getCityWeather(defaultCity)

        vm.cityList.observe(viewLifecycleOwner, {
            cityAdapter.updateItems(it)
        })

        return ui.root
    }
}