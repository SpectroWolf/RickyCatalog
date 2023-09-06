package com.spectro.tech.rickycatalog.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.spectro.tech.rickycatalog.model.domain.EpisodeDTO
import com.spectro.tech.rickycatalog.model.network.CharacterListResponse
import com.spectro.tech.rickycatalog.service.ApiInterface
import com.spectro.tech.rickycatalog.util.BasicApiResponse
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val userDataStore: DataStore<Preferences>
) : Repository {
    override suspend fun setDarkMode(darkMode: Boolean) {
        userDataStore.edit {
            it[DARK_MODE_THEME] = darkMode.toString()
        }
    }

    override suspend fun showDarkMode(): Boolean {
        val prefs = userDataStore.data.first()

        return prefs[DARK_MODE_THEME].toBoolean()
    }

    override suspend fun getEpisodeRange(episodesId: String): BasicApiResponse<List<EpisodeDTO>> {
        return try {

            val response = apiInterface.getEpisodeRange(episodesId)

            if (response.isSuccessful){
                response.body().let{
                    return@let BasicApiResponse.success(it)
                }
            } else {
                BasicApiResponse.error("Error code from https request was ${response.code()}", null)
            }

        } catch (e: Exception){
            BasicApiResponse.error(
                "An unknown error occurred when trying to get Character's List",
                null
            )
        }
    }

    override suspend fun getCharactersByPage(pageIndex: Int): BasicApiResponse<CharacterListResponse> {
        return try {

            val response = apiInterface.getCharactersByPage(pageIndex)

            if (response.isSuccessful){
                response.body().let{
                    return@let BasicApiResponse.success(it)
                }
            } else {
                BasicApiResponse.error("Error code from https request was ${response.code()}", null)
            }

        } catch (e: Exception){
            BasicApiResponse.error(
                "An unknown error occurred when trying to get Character's List",
                null
            )
        }
    }

    companion object {
        private val DARK_MODE_THEME = stringPreferencesKey("DARK_MODE_THEME")
    }
}