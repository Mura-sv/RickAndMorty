package com.example.rickandmorty.data.paging_data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.core.Constant.NETWORK_PAGE_SIZE
import com.example.rickandmorty.core.Constant.SERVER_STARTING_PAGE_INDEX
import com.example.rickandmorty.data.ApiService
import com.example.rickandmorty.data.toCharacters
import com.example.rickandmorty.domain.model.Characters
import retrofit2.HttpException
import java.io.IOException

class SearchPagingDataSource(
    private val query: String,
    private val api: ApiService,
) : PagingSource<Int,Characters>() {

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        val position = params.key ?: SERVER_STARTING_PAGE_INDEX
        return try {
            val response = api.fetchCharactersByNameAndPage(position, query)
            val data = response.body()?.results?.map { it.toCharacters() }?: emptyList()
            val nextKey = if (data.isEmpty()) {
                null
            } else position + (params.loadSize / NETWORK_PAGE_SIZE)
            val prevKey = if (position == SERVER_STARTING_PAGE_INDEX) null else position - 1
            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}