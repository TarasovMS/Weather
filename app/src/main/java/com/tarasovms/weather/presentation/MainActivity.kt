package com.tarasovms.weather.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tarasovms.weather.R
import com.tarasovms.weather.presentation.cities_fragment.CitiesFragment
import com.tarasovms.weather.presentation.map_fragment.MapFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav.menu.getItem(0).isCheckable = true
        setFragment(CitiesFragment())

        bottomNav.setOnItemSelectedListener {menu ->
            when(menu.itemId){
                R.id.bottomNav_cities -> {
                    setFragment(CitiesFragment())
                    true
                }
                R.id.bottomNav_map -> {
                    setFragment(MapFragment())
                    true
                }
                else -> false
            }
        }

    }

    private fun setFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager.beginTransaction()
        fragmentManager.replace(R.id.container,fragment)
        fragmentManager.commit()
    }
}