package com.jack.ravn_challenge.data.network

import com.jack.ravn_challenge.data.model.AllPeople
import com.jack.ravn_challenge.vo.Resource


interface ApolloService {
    suspend fun getAllPeople(cursor:String,count:Int): Resource<AllPeople>
}