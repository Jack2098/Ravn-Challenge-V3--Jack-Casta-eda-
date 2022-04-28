package com.jack.ravn_challenge.data.network

import com.jack.ravn_challenge.data.model.PersonModel

//import com.jack.ravn_challenge.data.model.PeopleSWModel


interface ApolloApiClient {

    suspend fun getAllPeople():List<PersonModel>
}