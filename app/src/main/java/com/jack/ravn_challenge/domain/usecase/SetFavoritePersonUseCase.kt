package com.jack.ravn_challenge.domain.usecase

import com.jack.ravn_challenge.data.PeopleRepository
import com.jack.ravn_challenge.data.model.PersonModel
import javax.inject.Inject


class SetFavoritePersonUseCase @Inject constructor(
    private val repository: PeopleRepository
) {
    suspend operator fun invoke(person:PersonModel){
        repository.setFavoritePerson(person)
    }
}