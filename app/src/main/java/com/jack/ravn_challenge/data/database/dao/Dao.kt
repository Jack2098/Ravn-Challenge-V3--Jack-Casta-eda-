package com.jack.ravn_challenge.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jack.ravn_challenge.data.database.entity.PersonEntity
import com.jack.ravn_challenge.data.database.entity.PersonWithVehicle
import com.jack.ravn_challenge.data.database.entity.VehicleEntity


@Dao
interface Dao {

    @Query("SELECT * FROM PersonEntity")
    suspend fun getAllPersonFavorite():List<PersonWithVehicle>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonFavorite(personEntity: PersonEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicle(vehicleEntity: VehicleEntity)


}