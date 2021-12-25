package com.example.rickandmorty.data.paging_data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.core.Constant.SERVER_STARTING_PAGE_INDEX
import com.example.rickandmorty.data.ApiService
import com.example.rickandmorty.data.toEpisodes
import com.example.rickandmorty.domain.model.Episodes
import retrofit2.HttpException
import java.io.IOException

class EpisodePagingDataSource(
    private val api: ApiService,
) : PagingSource<Int, Episodes>() {

    override fun getRefreshKey(state: PagingState<Int, Episodes>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episodes> {
        val position = params.key ?: SERVER_STARTING_PAGE_INDEX
        return try {
            val response = api.fetchAllEpisodesByPage(position)
            val data = response.body()?.results?.map { it.toEpisodes() } ?: emptyList()
            val nextKey = if (data.isEmpty()) {
                null
            } else position + 1

            LoadResult.Page(
                data = data,
                prevKey = if (position == SERVER_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}