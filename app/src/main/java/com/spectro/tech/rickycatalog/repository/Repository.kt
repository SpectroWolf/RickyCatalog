package com.spectro.tech.rickycatalog.repository

import com.spectro.tech.rickycatalog.model.domain.CharacterDTO
import com.spectro.tech.rickycatalog.model.domain.Comments
import com.spectro.tech.rickycatalog.model.domain.EpisodeDTO
import com.spectro.tech.rickycatalog.model.network.CharacterListResponse
import com.spectro.tech.rickycatalog.util.BasicApiResponse

interface Repository {

    suspend fun setDarkMode(darkMode: Boolean)

    suspend fun showDarkMode(): Boolean

    suspend fun getEpisodeRange(episodesId: String): BasicApiResponse<List<EpisodeDTO>>

    suspend fun getCharactersByPage(pageIndex: Int): BasicApiResponse<CharacterListResponse>

    suspend fun getAllComments(): Pair<List<Comments>, Boolean>

    suspend fun upSert(comments: Comments, onResult: (Boolean, String) -> Unit)

    suspend fun deleteComment(comments: Comments, onResult: (Boolean, String) -> Unit)


}