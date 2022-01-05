package com.tarasovms.weather.presentation.map_fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.model.*
import com.tarasovms.weather.R
import com.tarasovms.weather.data.remote.Default
import com.tarasovms.weather.data.remote.WeatherResponse
import com.tarasovms.weather.presentation.App
import com.tarasovms.weather.presentation.CommonViewModel


class MapFragment: Fragment(), OnMapReadyCallback, OnMarkerClickListener, OnMapClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var vm: CommonViewModel
    private lateinit var markerBoundaries:LatLngBounds
    private var locationPermissionGranted: Boolean = false
    private var center: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val viewFragment: View = inflater.inflate(R.layout.map_fragment,container,false)
        val mapFragment = (childFragmentManager.findFragmentById(R.id.google_map)) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val app = activity?.application as App
        vm = app.appComponent.getViewModel()

        return viewFragment
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (vm.cityList.value != null)
            listCities(vm.cityList.value!!)
        else {
            // Добавляем дефолтный маркер Ростов-на-Дону
            val defaultCity = LatLng(Default.defaultCity.latitude, Default.defaultCity.longitude)
            vm.getCityWeather(defaultCity)
            markerBoundaries = minBorder(defaultCity, defaultCity)
        }

        vm.cityList.observe(viewLifecycleOwner, {
            listCities(it)
        })

        mMap.setOnMapClickListener(this@MapFragment)
        mMap.setOnMarkerClickListener (this@MapFragment)

        getLocationPermission()
        updateLocationUI()
    }

    /* Проверяем раздрешения на локацию, если их нет - запрашиваем */
    private fun getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
            ActivityCompat.requestPermissions(requireActivity(), permissions,0)
        }
        else
            locationPermissionGranted = true
    }

    /* Отображаем/Скрываем "Моя локации" */
    private fun updateLocationUI() {
        try {
            if (locationPermissionGranted) {
                mMap.isMyLocationEnabled = true
                mMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                mMap.isMyLocationEnabled = false
                mMap.uiSettings.isMyLocationButtonEnabled = false
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    /* Клик по маркеру */
    override fun onMarkerClick(p0: Marker): Boolean {
        // здесь будет информационное окно
        return false
    }

    /* Перемещаем фокус камеры на главную метку */
    private fun minBorder(sw: LatLng, ne: LatLng): LatLngBounds{
        return LatLngBounds(sw, ne)
    }

    /* Клик по карте */
    override fun onMapClick(latLng: LatLng) {
        center = !center
        mMap.clear()
        vm.getCityWeather(latLng)
    }

    /* Перебираем каждый элемент списка*/
    private fun listCities(list: List<WeatherResponse>){
        list.map {
            val coordinates = LatLng(it.coordinates.latitude, it.coordinates.longitude)
            addMarkerCity(coordinates, it.cityName, center)
        }
    }

    /* Добаваляем маркер города */
    private fun addMarkerCity(latLng: LatLng, nameCity: String, center: Boolean) {
        val city = LatLng(latLng.latitude, latLng.longitude)

        if (center){
            this.center = !center
            this.markerBoundaries = minBorder(city, city)
            mMap = createMarker(mMap, city, nameCity, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerBoundaries.center, 10f))
        } else
            mMap = createMarker(mMap, city, nameCity, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
    }

    /* Создаем маркер */
    private fun createMarker(googleMap: GoogleMap, coordCity: LatLng, nameCity: String, bitmapDescriptor: BitmapDescriptor): GoogleMap{
        googleMap.addMarker(MarkerOptions()
            .position(coordCity)
            .title(resources.getString(R.string.weather_in)+" $nameCity "+resources.getString(R.string.for_today))
            .icon(bitmapDescriptor))
        return googleMap
    }
}