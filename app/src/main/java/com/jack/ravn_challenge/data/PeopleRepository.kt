package com.jack.ravn_challenge.data

import com.jack.ravn_challenge.data.database.dao.Dao
import com.jack.ravn_challenge.data.database.entity.PersonEntity
import com.jack.ravn_challenge.data.database.entity.VehicleEntity
import com.jack.ravn_challenge.data.model.*
import com.jack.ravn_challenge.data.network.ApolloServiceImpl
import com.jack.ravn_challenge.domain.model.Person
import com.jack.ravn_challenge.vo.Resource
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val api: ApolloServiceImpl,
    private val dao: Dao
) {

    suspend fun getAllPeople(cursor: String, count: Int): AllPeople {
        return api.getAllPeople(cursor, count)
    }

    suspend fun getPersonById(id: String): PersonModel {
        return api.getPersonById(id)
    }

    suspend fun getAllFavoritePeople(): List<PersonModel> {
        val response = dao.getAllPersonFavorite()
        return response.map {
            val vehicle = it.vehicleList.map { vehicle ->
                VehicleModel(vehicle.nameVE)
            }
            PersonModel(
                it.person.id,
                it.person.name,
                it.person.birthYear,
                it.person.eyeColor,
                it.person.hairColor,
                it.person.skinColor,
                it.person.homeworld,
                it.person.species,
                VehicleConnection(vehicle)
            )
        }
    }

    suspend fun setFavoritePerson(personModel: PersonModel) {
        val personEntity = PersonEntity(
            personModel.id!!,
            personModel.name,
            personModel.birthYear,
            personModel.eyeColor,
            personModel.hairColor,
            personModel.skinColor,
            personModel.homeworld,
            personModel.species
        )
        if (!personModel.vehicleConnection?.vehicles.isNullOrEmpty()){
            personModel.vehicleConnection?.vehicles?.forEach {
                val vehicleEntity = VehicleEntity(personId = personModel.id, nameVE = it.nameVM)
                dao.insertVehicle(vehicleEntity)
            }
        }
        dao.insertPersonFavorite(personEntity)
    }

}