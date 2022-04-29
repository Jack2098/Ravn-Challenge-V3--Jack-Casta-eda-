package com.jack.ravn_challenge.domain.usecase

import com.jack.ravn_challenge.data.PeopleRepository
import com.jack.ravn_challenge.data.model.AllPeople
import javax.inject.Inject

class GetPeopleUseCase @Inject constructor(
    private val repository: PeopleRepository
) {

    suspend operator fun invoke(cursor:String,count:Int): AllPeople{
        return repository.getAllPeople(cursor,count)
    }
}