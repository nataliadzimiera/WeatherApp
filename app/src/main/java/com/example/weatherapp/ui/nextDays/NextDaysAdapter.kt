package com.example.weatherapp.ui.nextDays

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.ItemDayBinding
import com.example.weatherapp.model.DetailList
import javax.inject.Inject

class NextDaysAdapter @Inject constructor() : RecyclerView.Adapter<NextDaysAdapter.ViewHolder>() {

    private var weatherList: List<DetailList> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDayBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(weatherList, position)
    }

    fun setWeatherList(weatherList: List<DetailList>) {
        this.weatherList = weatherList
        notifyDataSetChanged()
    }

     class ViewHolder(private val binding: ItemDayBinding) : RecyclerView.ViewHolder(binding.root){
         @SuppressLint("SetTextI18n")
         fun bind(list: List<DetailList>, position: Int){
             binding.tempDay.text = "${((list[position].main.temp)-273.15).toInt()}℃"
             binding.description.text = list[position].weather[0].description
         }
    }

}