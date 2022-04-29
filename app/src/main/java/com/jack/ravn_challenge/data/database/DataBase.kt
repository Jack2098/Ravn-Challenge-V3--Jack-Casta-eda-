package com.jack.ravn_challenge.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jack.ravn_challenge.data.database.dao.Dao
import com.jack.ravn_challenge.data.database.entity.PersonEntity
import com.jack.ravn_challenge.data.database.entity.VehicleEntity


@Database(entities = [PersonEntity::class,VehicleEntity::class], version = 1, exportSchema = false)
abstract class DataBase: RoomDatabase() {

    abstract fun getDao(): Dao

}