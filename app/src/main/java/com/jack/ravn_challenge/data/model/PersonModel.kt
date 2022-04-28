package com.jack.ravn_challenge.data.model


data class PersonModel(
    val id:String? = null,
    val name:String? = null,
    val birthYear: String? = null,
    val eyeColor: String? = null,
    val hairColor:String? = null,
    val homeworld: HomeworldModel? = null,
    val species: SpeciesModel? = null,
    val vehicleConnection: VehicleConnection? = null
)

//data class PeopleSWList()