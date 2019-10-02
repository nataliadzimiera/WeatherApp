package com.example.weatherapp.di.module

import com.example.weatherapp.di.ActivityScope
import com.example.weatherapp.ui.MainActivity
import com.example.weatherapp.ui.savedLocations.SavedLocationsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class ActivitiesModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun mainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector
    internal abstract fun savedLocationsActivity(): SavedLocationsActivity
}