package com.example.satelliteslocationdetermine.di

import android.content.Context
import android.content.res.AssetManager
import androidx.room.Room
import com.example.satelliteslocationdetermine.domain.database.SatelliteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideAssetManager(
        @ApplicationContext context: Context
    ): AssetManager {
        return context.assets
    }

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        SatelliteDatabase::class.java,
        "satellite_detail"
    ).build()

    @Singleton
    @Provides
    fun provideDao(db: SatelliteDatabase) = db.getSatelliteDetailDao()
}