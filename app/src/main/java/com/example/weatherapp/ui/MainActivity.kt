package com.example.weatherapp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.di.ViewModelInjectionFactory
import com.example.weatherapp.repository.Local.LocationsDatabase
import com.example.weatherapp.ui.savedLocations.SavedLocationsActivity
import com.example.weatherapp.ui.nextDays.NextDaysFragment
import com.example.weatherapp.ui.today.TabAdapter
import com.example.weatherapp.ui.today.TodayFragment
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import io.realm.Realm
import java.lang.Exception
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var mainActivityViewModel: MainActivityViewModel
    lateinit var fusedLocationClient: FusedLocationProviderClient
    var MY_PERMISSIONS_REQUEST_LOCATION = 99
    lateinit var realm: Realm

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<MainActivityViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainActivityViewModel = ViewModelProviders.of(this, viewModelInjectionFactory)
            .get(MainActivityViewModel::class.java)
        realm = Realm.getDefaultInstance()
        val viewPager = binding.viewPager
        setUpViewPager(viewPager)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val tabs = binding.tabLayout
        tabs.setupWithViewPager(viewPager)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (tabs.tabCount) {
                    0 -> showTodayFragment()
                    1 -> showNextDaysFragment()
                }
            }

        })

        checkPremission()
        autocompleteCityName()
        setSupportActionBar(binding.bottomAppBar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottomappbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.app_bar_settings -> {
                startActivity(Intent(this, SavedLocationsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun showTodayFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.viewPager, TodayFragment.newInstance()).commit()
    }

    fun showNextDaysFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.viewPager, NextDaysFragment.newInstance()).commit()
    }

    private fun setUpViewPager(viewPager: ViewPager) {
        val adapter = TabAdapter(supportFragmentManager)
        adapter.addFragment(TodayFragment(), getString(R.string.dzisiaj))
        adapter.addFragment(NextDaysFragment(), getString(R.string.kolejne_dni))
        binding.viewPager.adapter = adapter
    }

    fun autocompleteCityName() {
        Places.initialize(applicationContext, getString(R.string.api_key))

        val placesClient = Places.createClient(this)
        val autoFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment
        autoFragment.setPlaceFields(listOf(Place.Field.LAT_LNG, Place.Field.NAME))
        autoFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(p0: Place) {
                val latLng = p0.latLng
                mainActivityViewModel.lat = latLng?.latitude
                mainActivityViewModel.lng = latLng?.longitude
                mainActivityViewModel.city = p0.name
                mainActivityViewModel.weatherLocationLiveData.value = latLng
                mainActivityViewModel.name.value = p0.name
                showSnackBar()
            }

            override fun onError(p0: Status) {
                Toast.makeText(this@MainActivity, R.string.places_search_error, Toast.LENGTH_SHORT)
                    .show()
            }
        })


    }

    fun checkPremission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            getUserLocation()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation()
            } else {
                Toast.makeText(this, getString(R.string.noGPS), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getUserLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                mainActivityViewModel.weatherLocationLiveData.value =
                    LatLng(it.latitude, it.longitude)
            }
        }
    }

    fun showSnackBar() {
        val snackBar =
            Snackbar.make(binding.layout, getString(R.string.addToFavourite), Snackbar.LENGTH_LONG)
        snackBar.setAction(getString(R.string.save)) {
            val name = mainActivityViewModel.city
            val lat = mainActivityViewModel.lat
            val lng = mainActivityViewModel.lng

            try {
                realm.beginTransaction()
                val localizations = realm.createObject(LocationsDatabase::class.java)
                localizations.city = name
                localizations.lat = lat
                localizations.lng = lng
                realm.commitTransaction()
            } catch (ex: Exception){
                Log.e(getString(R.string.error), ex.toString())
                Toast.makeText(this, getString(R.string.errorRealm), Toast.LENGTH_SHORT).show()
            }
        }
        snackBar.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}

