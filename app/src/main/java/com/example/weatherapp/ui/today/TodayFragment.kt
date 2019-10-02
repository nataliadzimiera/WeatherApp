package com.example.weatherapp.ui.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.TodayFragmentBinding
import com.example.weatherapp.di.ViewModelInjectionFactory
import com.example.weatherapp.ui.MainActivityViewModel
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.today_fragment.*
import javax.inject.Inject


class TodayFragment : Fragment() {

    lateinit var todayFragmentBinding: TodayFragmentBinding

    lateinit var viewModel: TodayViewModel

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<TodayViewModel>

    lateinit var mainViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        todayFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.today_fragment, container, false)
        val view = todayFragmentBinding.root
        AndroidSupportInjection.inject(this)
        todayFragmentBinding.lifecycleOwner = this
        viewModel =
            ViewModelProviders.of(this, viewModelInjectionFactory).get(TodayViewModel::class.java)
        mainViewModel = activity?.run {
            ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        } ?: throw Exception(getString(R.string.invalidActivity))
        mainViewModel.weatherLocationLiveData.observe(this, Observer {
            val lat = it.latitude
            val lng = it.longitude
            viewModel.getWeather(lat, lng)
        })
        todayFragmentBinding.viewModel = viewModel
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentWeatherLiveData.observe(this, Observer {
            val temp = (it.main.temp - 273.15).toInt()
            val icon = it.weather.last().icon
            val desc = it.weather.last().description
            todayFragmentBinding.temp.text = temp.toString()
            todayFragmentBinding.description.text = desc
            Glide.with(this).load("https://openweathermap.org/img/wn/$icon.png")
                .into(weatherIcon)
        })
    }


    companion object {
        fun newInstance(): TodayFragment {
            return TodayFragment()
        }
    }

}
