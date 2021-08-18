package com.example.alarmmanager.di

import android.content.Context
import com.example.alarmmanager.data.InfoPreferences
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
    fun providePreferences(@ApplicationContext context: Context): InfoPreferences =
        InfoPreferences(context = context)
}