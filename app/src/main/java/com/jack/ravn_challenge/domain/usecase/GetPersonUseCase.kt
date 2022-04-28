package com.jack.ravn_challenge.domain.usecase

import com.jack.ravn_challenge.data.PeopleRepository
import com.jack.ravn_challenge.data.model.PersonModel

class GetPersonUseCase {

    private val repository = PeopleRepository()

    suspend operator fun invoke(id:String):PersonModel{
        return repository.getPersonById(id)
    }

}