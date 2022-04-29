package com.jack.ravn_challenge.data.database.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val idVE:Int = 0,
    val personId:String,
    val nameVE:String
)
