package com.jack.ravn_challenge.domain.usecase

import com.jack.ravn_challenge.data.PeopleRepository
import com.jack.ravn_challenge.data.model.PersonModel
import com.jack.ravn_challenge.domain.model.Person
import javax.inject.Inject

class GetFavoritePersonUseCase @Inject constructor(
    private val repository: PeopleRepository
) {

    suspend operator fun invoke():List<PersonModel>{
        return repository.getAllFavoritePeople()
    }
}