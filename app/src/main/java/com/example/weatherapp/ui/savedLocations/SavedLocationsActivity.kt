package com.example.weatherapp.ui.savedLocations

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivitySavedLocationsBinding
import com.example.weatherapp.di.ViewModelInjectionFactory
import com.example.weatherapp.repository.Local.Location
import com.example.weatherapp.repository.Local.LocationMapper
import com.example.weatherapp.repository.Local.LocationsDatabase
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_saved_locations.*
import javax.inject.Inject

class SavedLocationsActivity : DaggerAppCompatActivity() {

    lateinit var realm: Realm
    lateinit var binding: ActivitySavedLocationsBinding
    lateinit var savedLocationsViewModel: SavedLocationsViewModel

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<SavedLocationsViewModel>

    @Inject
    lateinit var adaper: SavedLocationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_saved_locations)
        savedLocationsViewModel = ViewModelProviders.of(this, viewModelInjectionFactory)
            .get(SavedLocationsViewModel::class.java)
        realm = Realm.getDefaultInstance()
        getAllLocations()
        initRecyclerView()

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun getAllLocations(): List<Location> {
        val locations = ArrayList<Location>()
        val mapper = LocationMapper()

        val all = realm.where(LocationsDatabase::class.java).findAll()

        for (noteRealm in all) {
            locations.add(mapper.fromRealm(noteRealm))
        }
        savedLocationsViewModel.locationsLiveData.value = locations

        return locations
    }

    fun initRecyclerView(){
        binding.recyclerRealm.layoutManager = LinearLayoutManager(recyclerRealm.context)
        binding.recyclerRealm.adapter = adaper
        registerObservers()
    }

    fun registerObservers(){
        savedLocationsViewModel.locationsLiveData.observe(this, Observer { locations->
            adaper.setLocationsList(locations)
        })
    }

}
