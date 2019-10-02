package com.example.weatherapp.di.module

import com.example.weatherapp.di.SessionComponent
import dagger.Module
import dagger.Provides

@Module(subcomponents = [SessionComponent::class])
internal class SessionProviderModule{
    @Provides
    internal fun providesSessionComponent(builder: SessionComponent.Builder): SessionComponent {
        return builder.build()
    }
}