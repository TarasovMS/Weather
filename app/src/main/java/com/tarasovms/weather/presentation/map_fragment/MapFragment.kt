package com.tarasovms.weather.presentation.map_fragment

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.tarasovms.weather.R
import com.tarasovms.weather.base.Constants
import com.tarasovms.weather.presentation.App


class MapFragment: Fragment(),
    OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMapClickListener {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient
    private lateinit var vm: MapViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        val viewFragment: View = inflater.inflate(R.layout.map_fragment,container,false)
        val mapFragment = (childFragmentManager.findFragmentById(R.id.google_map)) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val app = activity?.application as App
        vm = app.appComponent.getMapViewModel()

        // Создаем a PlacesClient
        Places.initialize(requireContext(), Constants.WEATHER_API_KEY)
        placesClient = Places.createClient(requireContext())

        // Создаем FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity() as Activity)

        vm.getLocationPermission.observe(viewLifecycleOwner,{
            getLocationPermission()
        })

        vm.bitmap = getBitmapDescriptorFromVector(requireContext(),R.drawable.my_location_24)
        return viewFragment
    }

    /* Карта готова */
    override fun onMapReady(googleMap: GoogleMap) {
        getLocationPermission()

        vm.mapReady(googleMap,
            this,
            this@MapFragment,
            this@MapFragment,
            fusedLocationProviderClient)
    }

    /* Проверяем раздрешения на локацию, если их нет - запрашиваем */
    private fun getLocationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(requireContext(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            val permissions = arrayOf(ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION)
            ActivityCompat.requestPermissions(requireActivity(), permissions,0)
        }
        else
            vm.locationPermissionGranted = true
    }

    /* Переиспользуем метод */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        vm.requestPermissionsResult(requestCode,grantResults)
    }

    /* Создаем растровое изображение */
    private fun getBitmapDescriptorFromVector(context: Context, @DrawableRes vectorDrawableResourceId: Int): BitmapDescriptor {

        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        val bitmap = Bitmap.createBitmap(vectorDrawable!!.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)

        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    /* Клик по маркеру */
    override fun onMarkerClick(p0: Marker): Boolean {
        val viewCustomInfoWindow = requireActivity().layoutInflater.inflate(R.layout.custom_info_window,null)
        vm.mMap.setInfoWindowAdapter(CustomInfoWindow(viewCustomInfoWindow))
        return false
    }

    /* Клик по карте */
    override fun onMapClick(latLng: LatLng) {
        vm.center = true
        vm.mapClear()
        vm.getCityWeather(latLng)
    }
}