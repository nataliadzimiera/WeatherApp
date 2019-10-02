package com.example.weatherapp.di.module

import com.example.weatherapp.App
import com.example.weatherapp.di.SessionComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector

@Module(subcomponents = [SessionComponent::class], includes = [SessionProviderModule::class])
internal abstract class SessionModule {
    @Binds
    internal abstract fun injector(component: SessionComponent): AndroidInjector<App>
}
