package com.spectro.tech.rickycatalog.repository

import com.spectro.tech.rickycatalog.model.domain.EpisodeDTO
import com.spectro.tech.rickycatalog.model.network.CharacterListResponse
import com.spectro.tech.rickycatalog.util.BasicApiResponse

interface Repository {

    suspend fun setDarkMode(darkMode: Boolean)

    suspend fun showDarkMode(): Boolean

    suspend fun getEpisodeRange(episodesId: String): BasicApiResponse<List<EpisodeDTO>>

    suspend fun getCharactersByPage(pageIndex: Int): BasicApiResponse<CharacterListResponse>
}