package com.jack.ravn_challenge.di

import android.content.Context
import androidx.room.Room
import com.jack.ravn_challenge.data.database.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "star_war"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, DataBase::class.java,DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDao(db:DataBase) = db.getDao()
}