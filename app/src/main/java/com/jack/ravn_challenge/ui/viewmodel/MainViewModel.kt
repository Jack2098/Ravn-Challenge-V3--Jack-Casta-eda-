package com.jack.ravn_challenge.ui.viewmodel

import androidx.lifecycle.*
import com.apollographql.apollo3.exception.ApolloException
import com.jack.ravn_challenge.data.model.AllPeople
import com.jack.ravn_challenge.data.model.PersonModel
import com.jack.ravn_challenge.domain.usecase.GetFavoritePersonUseCase
import com.jack.ravn_challenge.domain.usecase.GetPeopleUseCase
import com.jack.ravn_challenge.domain.usecase.GetPersonUseCase
import com.jack.ravn_challenge.domain.usecase.SetFavoritePersonUseCase
import com.jack.ravn_challenge.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    var getPeopleUseCase: GetPeopleUseCase,
    var getPersonUseCase: GetPersonUseCase,
    val setFavoritePerson: SetFavoritePersonUseCase,
    val getFavoritePeople: GetFavoritePersonUseCase
) :ViewModel() {

    val peopleModel = MutableLiveData<Resource<AllPeople?>>()
    val personModel = MutableLiveData<Resource<PersonModel>>()


    fun getAllPeople(cursor:String, count:Int) {

        var newcursor =cursor
        var result:AllPeople


        viewModelScope.launch {

            peopleModel.postValue(Resource.Loading)
            try {
                while (true){
                    result =  getPeopleUseCase(newcursor,count)
                    peopleModel.postValue(Resource.Success(result))

                    if (!result.pageInfo?.hasNextPage!!){
                        break
                    }else{
                        newcursor = result.pageInfo?.endCursor!!
                    }
                }
            }catch (e:ApolloException){
                peopleModel.postValue(Resource.Failure(e))
            }
        }
    }

    fun getPerson(id:String){
        viewModelScope.launch {
            personModel.postValue(Resource.Loading)
            try {
                val  response  = getPersonUseCase(id)
                personModel.postValue(Resource.Success(response))
            }catch (e:ApolloException){
                personModel.postValue(Resource.Failure(e))
            }
        }
    }

    fun saveFavoritePerson(personModel: PersonModel) {
        viewModelScope.launch {
            setFavoritePerson(personModel)
        }
    }

    fun favoritePeople()= liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Resource.Loading)
        try {
            emit(Resource.Success(getFavoritePeople()))
        }catch (e:Exception){
            emit(Resource.Failure(e))
        }
    }

    //val fetchPeopleList = peopleData.

    /*fun getAllPeople()=liveData<Resource<List<GetAllPeopleQuery.Person>>>(viewModelScope.coroutineContext + Dispatchers.Main){
        emit(Resource.Loading)
        try {
            val response = apolloClient.query(GetAllPeopleQuery()).execute()
            val newPeople = response.data?.allPeople?.people?.filterNotNull()
            emit(newPeople!!)
        }catch (e:ApolloException){
            emit(Resource.Failure(e))
        }
    }*/
}