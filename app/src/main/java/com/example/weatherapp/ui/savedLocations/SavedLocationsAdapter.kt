package com.example.weatherapp.ui.savedLocations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemDayBinding
import com.example.weatherapp.databinding.ItemLocationBinding
import com.example.weatherapp.repository.Local.Location
import com.example.weatherapp.repository.Local.LocationsDatabase
import javax.inject.Inject

class SavedLocationsAdapter @Inject constructor(): RecyclerView.Adapter<SavedLocationsAdapter.ViewHolder>() {

    private var locationsList: List<Location> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return  locationsList.size
    }

    fun setLocationsList(locationsList: List<Location>){
        this.locationsList = locationsList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(locationsList[position])
    }

    class ViewHolder(private val binding: ItemLocationBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(location: Location){
                binding.textCity.text = location.name
                binding.textLat.text = location.lat.toString()
                binding.textLng.text = location.lng.toString()
            }
    }

}
