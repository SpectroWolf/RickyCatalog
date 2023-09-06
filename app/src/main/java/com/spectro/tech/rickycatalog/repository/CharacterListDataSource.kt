package com.spectro.tech.rickycatalog.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.spectro.tech.rickycatalog.model.domain.CharacterDTO
import javax.inject.Inject

class CharacterListDataSource @Inject constructor(
    private val repository: Repository
) : PagingSource<Int, CharacterDTO>() {

    override fun getRefreshKey(state: PagingState<Int, CharacterDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDTO> {

        val page = params.key ?: DEFAULT_INIT_KEY
        val prevPage = if (page == 1) null else page - 1

        return try {
            val response = repository.getCharactersByPage(page)

            LoadResult.Page(
                data = response.data?.results!!,
                prevKey = prevPage,
                nextKey = getPageIndexFromUrl(response.data.info.next)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun getPageIndexFromUrl(url: String?): Int? {
        return url?.split("?page=")?.get(1)?.toInt()
    }

    companion object {
        const val DEFAULT_INIT_KEY = 1
    }
}