package com.example.rickandmorty.data.paging_data_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.core.Constant.SERVER_STARTING_PAGE_INDEX
import com.example.rickandmorty.data.ApiService
import com.example.rickandmorty.data.toCharacters
import com.example.rickandmorty.domain.model.Characters
import retrofit2.HttpException
import java.io.IOException


class CharactersPagingDataSource(
    private val api: ApiService,
) : PagingSource<Int, Characters>() {

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {

        try {
            val position = params.key ?: SERVER_STARTING_PAGE_INDEX
            Log.e("page: ", position.toString())
            val response = api.fetchAllCharacters(position)
            val data = response.body()?.results?.map { it.toCharacters() } ?: emptyList()

            if (response.isSuccessful) {
                val nextKey = if (data.isEmpty()) {
                    null
                } else position + 1
                val prevKey = if (position > 1) position - 1 else null

                return LoadResult.Page(
                    data = data,
                    prevKey = prevKey,
                    nextKey = nextKey
                )
            } else return LoadResult.Error(HttpException(response))

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }


}