package com.jack.ravn_challenge.ui.viewmodel

import androidx.lifecycle.*
import com.apollographql.apollo3.exception.ApolloException
import com.jack.ravn_challenge.core.GraphQLInstance
import com.jack.ravn_challenge.data.model.AllPeople
import com.jack.ravn_challenge.domain.GetPeopleUseCase
import com.jack.ravn_challenge.vo.Resource
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {

    val peopleModel = MutableLiveData<Resource<AllPeople?>>()

    var getPeopleUseCase = GetPeopleUseCase()

    fun onCreate(cursor:String,count:Int) {

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


    val apolloClient = GraphQLInstance.get()

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