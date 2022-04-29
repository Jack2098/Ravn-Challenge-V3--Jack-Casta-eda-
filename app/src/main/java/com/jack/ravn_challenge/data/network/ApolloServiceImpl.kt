package com.jack.ravn_challenge.data.network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.jack.ravn_challenge.GetAllPeopleQuery
import com.jack.ravn_challenge.GetPersonByIdQuery
import com.jack.ravn_challenge.data.model.*
import javax.inject.Inject

class ApolloServiceImpl @Inject constructor(
    private val apolloClient: ApolloClient
) :ApolloService {


    override suspend fun getAllPeople(cursor: String, count: Int): AllPeople {

        val response = getQueryAllPeople(cursor, count)

        val personList = convertPersonList(response)

        val pageInfo = PageInfoModel(
            response.data?.allPeople?.pageInfo?.endCursor!!,
            response.data?.allPeople?.pageInfo?.hasNextPage!!
        )

        return AllPeople(personList, pageInfo)
    }

    override suspend fun getPersonById(id: String): PersonModel {
        val person = getQueryPersonById(id).data?.person
        val homeworld = HomeworldModel(person?.homeworld?.name!!)
        val species = SpeciesModel(person.species?.name)
        val listVehicle = person.vehicleConnection?.vehicles!!.map {
            VehicleModel(it?.name!!)
        }
        val vehicleConnection = VehicleConnection(listVehicle)
        return PersonModel(person.id, person.name!!,person.birthYear!!,person.eyeColor!!,person.hairColor!!,person.skinColor,homeworld,species,vehicleConnection)
    }

    private suspend fun getQueryAllPeople(cursor: String, count: Int): ApolloResponse<GetAllPeopleQuery.Data> {
        return apolloClient.query(
            GetAllPeopleQuery(
                cursor = Optional.presentIfNotNull(cursor),
                count = Optional.presentIfNotNull(count)
            )
        ).execute()
    }

    private suspend fun getQueryPersonById(id: String): ApolloResponse<GetPersonByIdQuery.Data> {
        return apolloClient.query(
            GetPersonByIdQuery(
                id = Optional.presentIfNotNull(id)
            )
        ).execute()
    }

    private fun convertPersonList(response:ApolloResponse<GetAllPeopleQuery.Data>):List<PersonModel>{
        val personList = response.data?.allPeople?.people!!.map { person->
            val homeworld = HomeworldModel(person?.homeworld?.name!!)
            val species = SpeciesModel(person.species?.name)
            val listVehicle = person.vehicleConnection?.vehicles!!.map {
                VehicleModel(it?.name!!)
            }
            val vehicleConnection = VehicleConnection(listVehicle)
            PersonModel(person.id, person.name!!,person.birthYear!!,person.eyeColor!!,person.hairColor!!,person.skinColor,homeworld,species,vehicleConnection)
        }
        return personList
    }


    /*suspend fun getAllPeople(cursor:String="",count:Int=0):AllPeople{
        return withContext(Dispatchers.IO){
            val response = apolloClient.query(
                GetAllPeopleQuery(
                    cursor = Optional.presentIfNotNull(cursor),
                    count = Optional.presentIfNotNull(count)
                )
            ).execute()
            val personList = response.data?.allPeople?.people!!.map { person->
                val homeworld = HomeworldModel(person?.homeworld?.name!!)
                val species = SpeciesModel(person?.species?.name)
                val listVehicle = person.vehicleConnection?.vehicles!!.map {
                    VehicleModel(it?.name!!)
                }
                val vehicleConnection = VehicleConnection(listVehicle)
                PersonModel(person?.id!!, person?.name!!,person?.birthYear!!,person?.eyeColor!!,person?.hairColor!!,homeworld,species,vehicleConnection)
            }

            val pageInfo = PageInfoModel(response.data?.allPeople?.pageInfo?.endCursor!!,response.data?.allPeople?.pageInfo?.hasNextPage!!)

            val allPeople = AllPeople(personList,pageInfo)
            allPeople
        }
    }*/
}