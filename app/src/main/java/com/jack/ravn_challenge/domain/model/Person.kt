package com.jack.ravn_challenge.domain.model

import com.jack.ravn_challenge.data.model.HomeworldModel
import com.jack.ravn_challenge.data.model.SpeciesModel
import com.jack.ravn_challenge.data.model.VehicleConnection
import java.io.Serializable


class Person(
    val id:String?,
    val name:String?,
    val birthYear: String?,
    val eyeColor: String?,
    val hairColor:String?,
    val skinColor:String?,
    val homeworld: HomeworldModel?,
    val species: SpeciesModel?,
    val vehicleConnection: VehicleConnection?
):Serializable