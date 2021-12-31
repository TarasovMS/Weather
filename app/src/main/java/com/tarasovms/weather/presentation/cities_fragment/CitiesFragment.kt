package com.tarasovms.weather.presentation.cities_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tarasovms.weather.databinding.ListCitiesFragmentBinding
import com.tarasovms.weather.presentation.App
import kotlin.random.Random

class CitiesFragment: Fragment() {

    lateinit var vm: CitiesViewModel
    lateinit var ui: ListCitiesFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        super.onCreateView(inflater, container, savedInstanceState)

        val app = (context?.applicationContext as App)
        vm = CitiesViewModel(app)
        vm.fetchWeatherCity((activity?.application as App).weatherApi)

        val cities = listOf("Samara","Moscow","Kirov", "Volgograd", "New York", "Kiev", "Omsk", "Balakovo", "Vienna")

        ui = ListCitiesFragmentBinding.inflate(layoutInflater)
        ui.button1.setOnClickListener{
            vm.name.value = cities.random()
            ui.edText.setText(vm.name.value)

        }
        ui.button2.setOnClickListener {
            vm.name.value= cities.random()
            ui.edText.setText(vm.name.value)
        }

        vm.name.observe(requireActivity(), {
            vm.fetchWeatherCity((activity?.application as App).weatherApi)
        })


        vm.nameCity.observe(requireActivity(),{
            ui.textViewCityHttp.text = it
        })

        vm.tempory.observe(requireActivity(),{
            ui.textViewtemperatyre.text = it.toString()
        })

        return ui.root
    }


}