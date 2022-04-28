package com.jack.ravn_challenge.data.network

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.jack.ravn_challenge.GetAllPeopleQuery
import com.jack.ravn_challenge.core.GraphQLInstance
import com.jack.ravn_challenge.data.model.*
import com.jack.ravn_challenge.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.internal.wait

class ApolloServiceImpl:ApolloService {

    private val apolloClient = GraphQLInstance.get()


    override suspend fun getAllPeople(cursor: String, count: Int): AllPeople {

        val response = getQuery(cursor,count)

        val personList = convertPersonList(response)

        val pageInfo = PageInfoModel(response.data?.allPeople?.pageInfo?.endCursor!!,response.data?.allPeople?.pageInfo?.hasNextPage!!)

        val allPeople = AllPeople(personList,pageInfo)

        return allPeople
    }

    suspend fun getQuery(cursor:String, count:Int):ApolloResponse<GetAllPeopleQuery.Data>{
        val response = apolloClient.query(
            GetAllPeopleQuery(
                cursor = Optional.presentIfNotNull(cursor),
                count = Optional.presentIfNotNull(count)
            )
        ).execute()

        return response
    }

    fun convertPersonList(response:ApolloResponse<GetAllPeopleQuery.Data>):List<PersonModel>{
        val personList = response.data?.allPeople?.people!!.map { person->
            val homeworld = HomeworldModel(person?.homeworld?.name!!)
            val species = SpeciesModel(person?.species?.name)
            val listVehicle = person.vehicleConnection?.vehicles!!.map {
                VehicleModel(it?.name!!)
            }
            val vehicleConnection = VehicleConnection(listVehicle)
            PersonModel(person?.id!!, person?.name!!,person?.birthYear!!,person?.eyeColor!!,person?.hairColor!!,homeworld,species,vehicleConnection)
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