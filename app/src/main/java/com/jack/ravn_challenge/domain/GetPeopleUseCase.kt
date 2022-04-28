package com.jack.ravn_challenge.domain

import com.jack.ravn_challenge.data.PeopleRepository
import com.jack.ravn_challenge.data.model.AllPeople
import com.jack.ravn_challenge.data.model.PersonModel
import com.jack.ravn_challenge.vo.Resource

class GetPeopleUseCase {

    private val repository = PeopleRepository()

    suspend operator fun invoke(cursor:String,count:Int): AllPeople{
        return repository.getAllPeople(cursor,count)
    }
}