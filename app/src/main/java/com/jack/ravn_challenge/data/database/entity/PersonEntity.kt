package com.jack.ravn_challenge.data.database.entity

import androidx.annotation.NonNull
import androidx.room.*
import com.jack.ravn_challenge.data.model.HomeworldModel
import com.jack.ravn_challenge.data.model.SpeciesModel

@Entity
data class PersonEntity(
    @PrimaryKey
    @NonNull
    val id:String,
    val name:String?,
    val birthYear: String?,
    val eyeColor: String?,
    val hairColor:String?,
    val skinColor:String?,
    @Embedded
    val homeworld: HomeworldModel?,
    @Embedded
    val species: SpeciesModel?,
)

data class PersonWithVehicle(
    @Embedded
    val person:PersonEntity,
    @Relation(parentColumn = "id", entityColumn = "personId")
    val vehicleList: List<VehicleEntity>
)
