package com.jack.ravn_challenge.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jack.ravn_challenge.data.model.AllPeople
import com.jack.ravn_challenge.data.model.PersonModel
import com.jack.ravn_challenge.data.network.ApolloService
import com.jack.ravn_challenge.domain.GetPeopleUseCase
import com.jack.ravn_challenge.vo.Resource

class PeoplePagingSour(val getPeopleUseCase: GetPeopleUseCase):PagingSource<String,PersonModel>() {

companion object{
    private const val FIRST_PAGE_INDEX = ""
    private const val COUNT = 5
}

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PersonModel> {
        return try {
            val nextPage = params.key ?: FIRST_PAGE_INDEX
            val response = getPeopleUseCase(nextPage,COUNT)
            var nextPageCursor:String = ""
            var result:AllPeople? = null
            when(response){
                is Resource.Success->{
                    result = response.data
                }
            }
            val prevPageCursor = nextPage
            if (result?.pageInfo?.hasNextPage!!){
                nextPageCursor = result?.pageInfo?.endCursor!!
            }
            LoadResult.Page(
                data = result?.people!!,
                prevKey = prevPageCursor,
                nextKey = nextPageCursor
            )

        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, PersonModel>): String? {
        return null
    }

}