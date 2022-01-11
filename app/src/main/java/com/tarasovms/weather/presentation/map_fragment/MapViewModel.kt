package com.tarasovms.weather.presentation.map_fragment

import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.tarasovms.weather.data.remote.Default
import com.tarasovms.weather.data.remote.WeatherResponse
import com.tarasovms.weather.presentation.App
import com.tarasovms.weather.presentation.CommonViewModel
import javax.inject.Inject
import kotlin.math.roundToInt


class MapViewModel @Inject constructor (application: App): CommonViewModel(application) {

    lateinit var mMap: GoogleMap
    lateinit var bitmap : BitmapDescriptor
    var locationPermissionGranted: Boolean = false
    var getLocationPermission = MutableLiveData<Boolean>()
    var center: Boolean = true

    companion object{
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val DEFAULT_ZOOM = 10f
    }

    /* Карта готова */
    fun mapReady(googleMap: GoogleMap,
                 lifecycleOwner: LifecycleOwner,
                 mapClick: GoogleMap.OnMapClickListener?,
                 markerClick: GoogleMap.OnMarkerClickListener?,
                 fusedLocationProviderClient: FusedLocationProviderClient) {

        mMap = googleMap
        center = true

        if (cityList.value != null)
            listCities(cityList.value!!)
        else
            getDeviceLocation(fusedLocationProviderClient)

        updateLocation()

        cityList.observe(lifecycleOwner, {
            listCities(it)
        })

        mMap.setOnMapClickListener(mapClick)
        mMap.setOnMarkerClickListener(markerClick)
    }

    /* Получаем место нахождение девайса */
    private fun getDeviceLocation(fusedLocationProviderClient: FusedLocationProviderClient) {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        // Устанавливаем положение камеры на карте в текущее местоположение устройства
                        val lastKnownLocation: Location? = task.result
                        if (lastKnownLocation != null) {
                            val location = LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
                            getCityWeather(location)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, DEFAULT_ZOOM))
                        }
                    }
                    // Если нету данных, то загружаем дэфолтную локацию
                    else
                        defaultLocation()
                }
            }
            // Загружаем дэфолтную локацию, если нет разрешения
            else
                defaultLocation()

        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    fun requestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocation()
    }

    private fun defaultLocation() {
        val defaultCity = LatLng(Default.defaultCity.latitude, Default.defaultCity.longitude)
        getCityWeather(defaultCity)
        mMap.uiSettings.isMyLocationButtonEnabled = false
    }

    /* Отображаем/Скрываем "Моя локации" */
    private fun updateLocation() {
        try {
            if (locationPermissionGranted) {
                mMap.isMyLocationEnabled = true
                mMap.isMyLocationEnabled = false
                mMap.uiSettings.isMyLocationButtonEnabled = true
            } else {
                mMap.isMyLocationEnabled = false
                mMap.uiSettings.isMyLocationButtonEnabled = false
                getLocationPermission.value = true
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    fun mapClear() {
        mMap.clear()
    }

    /* Перебираем каждый элемент списка*/
    private fun listCities(list: List<WeatherResponse>){
        list.map {
            val coordinates = LatLng(it.coordinates.latitude, it.coordinates.longitude)
            addMarkerCity(coordinates, it)
        }
    }

    /* Добаваляем маркер города */
    private fun addMarkerCity(latLng: LatLng, response: WeatherResponse) {
        val city = LatLng(latLng.latitude, latLng.longitude)

        if (center){
            this.center = false
            mMap = createMarker(mMap, city, response, bitmap)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city, DEFAULT_ZOOM))
        } else
            mMap = createMarker(mMap, city, response, BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_ORANGE))
    }

    /* Создаем маркер */
    private fun createMarker(googleMap: GoogleMap,
                             coordCity: LatLng,
                             weather: WeatherResponse,
                             bitmapDescriptor: BitmapDescriptor): GoogleMap {

        var weatherConditions = ""
        weather.weather.map { weatherConditions = it.description }

        googleMap.addMarker(
            MarkerOptions()
                .position(coordCity)
                .icon(bitmapDescriptor)
                .title(weather.cityName)
                .snippet(" Погода на сегодня : \n" +
                        "\n"+
                        " Температура = ${weather.main.temp.roundToInt()} °C ,\n" +
                        " Атмосферное давление = ${weather.main.pressure}гПа ,\n" +
                        " Влажность = ${weather.main.humidity}% ,\n" +
                        " Ветер = ${weather.wind.speed}м/с ,\n" +
                        " Погодные условия = ${weatherConditions.replaceFirstChar { it.uppercase() }}"))!!
        return googleMap
    }
}