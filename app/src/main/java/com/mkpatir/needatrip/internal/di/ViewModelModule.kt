package com.mkpatir.needatrip.internal.di

import com.mkpatir.needatrip.api.AppRepository
import com.mkpatir.needatrip.api.AppService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    @Singleton
    fun provideAppRepository(appService: AppService) = AppRepository(appService)

}