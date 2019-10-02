package com.example.weatherapp.di.module

import com.example.weatherapp.di.FragmentScope
import com.example.weatherapp.ui.nextDays.NextDaysFragment
import com.example.weatherapp.ui.today.TodayFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class MainActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun todayFragment(): TodayFragment

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun nextDaysFragment(): NextDaysFragment
}