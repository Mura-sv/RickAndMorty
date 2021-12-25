package com.example.rickandmorty.data.paging_data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.core.Constant
import com.example.rickandmorty.data.ApiService
import com.example.rickandmorty.data.toLocations
import com.example.rickandmorty.domain.model.Locations
import retrofit2.HttpException
import java.io.IOException

class LocationPagingDataSource(private val api: ApiService) : PagingSource<Int, Locations>() {

    override fun getRefreshKey(state: PagingState<Int, Locations>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Locations> {
        val position = params.key ?: Constant.SERVER_STARTING_PAGE_INDEX
        return try {
            val response = api.fetchAllLocationsByPage(position)
            val data = response.body()?.results?.map { it.toLocations() } ?: emptyList()
            val nextKey = if (data.isEmpty()) {
                null
            } else position + 1

            LoadResult.Page(
                data = data,
                prevKey = if (position == Constant.SERVER_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}