package com.jack.ravn_challenge.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.apollographql.apollo3.exception.ApolloException
import com.jack.ravn_challenge.core.GraphQLInstance
import com.jack.ravn_challenge.data.model.AllPeople
import com.jack.ravn_challenge.data.model.PageInfoModel
import com.jack.ravn_challenge.data.model.PersonModel
import com.jack.ravn_challenge.domain.GetPeopleUseCase
import com.jack.ravn_challenge.paging.PeoplePagingSour
import com.jack.ravn_challenge.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {

    val peopleModel = MutableLiveData<Resource<AllPeople>>()

    var getPeopleUseCase = GetPeopleUseCase()

    fun onCreate(cursor:String,count:Int) {

        viewModelScope.launch {
            peopleModel.postValue(Resource.Loading)
            try {
                val result =  getPeopleUseCase(cursor,count)
                peopleModel.postValue(result)
            }catch (e:ApolloException){
                peopleModel.postValue(Resource.Failure(e))
            }
        }
    }

    fun getListData(): Flow<PagingData<PersonModel>> {
        return Pager(config = PagingConfig(pageSize = 5),
            pagingSourceFactory = {PeoplePagingSour(getPeopleUseCase)}).flow.cachedIn(viewModelScope)
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