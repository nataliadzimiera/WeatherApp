package com.example.weatherapp.di

import com.example.weatherapp.App
import com.example.weatherapp.di.module.ActivitiesModule
import com.example.weatherapp.di.module.DomainToolsModule
import com.example.weatherapp.di.module.SessionModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SessionModule::class,
        DomainToolsModule::class,
        ActivitiesModule::class,
        AndroidSupportInjectionModule::class
    ]
)


interface AppComponent : AndroidInjector<App> {
    fun androidInjector(): AndroidInjector<App>

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>() {
        abstract override fun build(): AppComponent
    }
}