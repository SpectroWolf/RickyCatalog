package com.spectro.tech.rickycatalog.service

import com.spectro.tech.rickycatalog.model.domain.EpisodeDTO
import com.spectro.tech.rickycatalog.model.network.CharacterListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("episode/{episodesId}")
    suspend fun getEpisodeRange(
        @Path("episodesId") episodesId: String
    ): Response<List<EpisodeDTO>>

    @GET("character")
    suspend fun getCharactersByPage(
        @Query("page") pageIndex : Int
    ): Response<CharacterListResponse>

}