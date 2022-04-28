package com.jack.ravn_challenge.data

import com.jack.ravn_challenge.data.model.AllPeople
import com.jack.ravn_challenge.data.network.ApolloServiceImpl
import com.jack.ravn_challenge.vo.Resource

class PeopleRepository {

    private val api = ApolloServiceImpl()

    suspend fun getAllPeople(cursor: String = "", count: Int = 0): Resource<AllPeople> {
        return api.getAllPeople(cursor, count)
    }

}