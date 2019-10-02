package com.example.weatherapp.ui.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import com.example.weatherapp.model.CurrentWeather
import com.example.weatherapp.repository.Remote.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TodayViewModel @Inject constructor(private val api: Api): ViewModel() {
    private val _weatherLiveData = MutableLiveData<CurrentWeather>()
    val currentWeatherLiveData: LiveData<CurrentWeather>
        get() = _weatherLiveData

    val compositeDisposable = CompositeDisposable()

    fun getWeather(lat: Double, lng: Double) {
        compositeDisposable.add(
            api.getCurrentWeather(lat, lng, "e986870c7502d3986c6f275c4bcea618")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _weatherLiveData.value = it
                    },
                    { it.printStackTrace() },
                    {}
                )
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}
