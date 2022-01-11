package com.tarasovms.weather.presentation.cities_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        val app = context?.applicationContext as App

        cityAdapter = CityAdapter(listOf(), object: OnCityClickListener{
            override fun clickedCityItem(weatherResponse: WeatherResponse, position: Int) {
                weatherResponse.expandable = !weatherResponse.expandable
                cityAdapter.updateItems(position)
            }
        })
        ui = ListCitiesFragmentBinding.inflate(layoutInflater)
        ui.recyclerViewCity.adapter = cityAdapter

        vm = app.appComponent.getMapViewModel()

        if (vm.cityList.value == null) vm.getCityWeather(defaultCity)
        else cityAdapter.updateItems(vm.cityList.value!!)

        vm.cityList.observe(viewLifecycleOwner, {
            cityAdapter.updateItems(it)
        })

        return ui.root
    }
}