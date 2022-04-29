package com.jack.ravn_challenge.data.model

import com.jack.ravn_challenge.data.database.entity.PersonWithVehicle
import com.jack.ravn_challenge.domain.model.Person


data class PersonModel(
    val id: String? = null,
    val name: String? = null,
    val birthYear: String? = null,
    val eyeColor: String? = null,
    val hairColor: String? = null,
    val skinColor: String? = null,
    val homeworld: HomeworldModel? = null,
    val species: SpeciesModel? = null,
    val vehicleConnection: VehicleConnection? = null
)

fun PersonModel.toDomain() = Person(
    id,
    name,
    birthYear,
    eyeColor,
    hairColor,
    skinColor,
    homeworld,
    species,
    vehicleConnection
)

//data class PeopleSWList()