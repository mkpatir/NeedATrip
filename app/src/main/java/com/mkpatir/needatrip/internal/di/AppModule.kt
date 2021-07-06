package com.mkpatir.needatrip.internal.di

import android.content.Context
import com.mkpatir.needatrip.internal.helpers.SharedPrefHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPrefHelper(@ApplicationContext context: Context) = SharedPrefHelper(context)

}