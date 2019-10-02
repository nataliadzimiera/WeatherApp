package com.example.weatherapp.ui.nextDays

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.model.FiveDaysWeather
import com.example.weatherapp.repository.Remote.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NextDaysViewModel @Inject constructor(private val api: Api) : ViewModel() {


    val compositeDisposable = CompositeDisposable()
    private val _weatherLiveData = MutableLiveData<FiveDaysWeather>()
    val fiveDaysWeatherLiveData: LiveData<FiveDaysWeather>
        get() = _weatherLiveData


    fun getFiveDaysWeather(lat: Double, lng: Double) {
        compositeDisposable.add(
            api.get5DayWeather(lat, lng,  "e986870c7502d3986c6f275c4bcea618")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _weatherLiveData.value = it
                    },
                    {
                        it.printStackTrace()
                    },
                    {}
                )

        )
    }


    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}
