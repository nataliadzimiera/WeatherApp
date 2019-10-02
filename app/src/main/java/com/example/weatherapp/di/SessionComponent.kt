package com.example.weatherapp.di

import com.example.weatherapp.App
import com.example.weatherapp.di.module.ActivitiesModule
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@SessionScope
@Subcomponent(
    modules = [
        ActivitiesModule::class,
        AndroidSupportInjectionModule::class
    ])
interface SessionComponent : AndroidInjector<App> {
    @Subcomponent.Builder
    abstract class Builder {
        abstract fun build(): SessionComponent
    }
}