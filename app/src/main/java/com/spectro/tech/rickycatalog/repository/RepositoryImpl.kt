package com.spectro.tech.rickycatalog.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.firestore.FirebaseFirestore
import com.spectro.tech.rickycatalog.model.domain.Comments
import com.spectro.tech.rickycatalog.model.domain.EpisodeDTO
import com.spectro.tech.rickycatalog.model.network.CharacterListResponse
import com.spectro.tech.rickycatalog.service.ApiInterface
import com.spectro.tech.rickycatalog.util.BasicApiResponse
import com.spectro.tech.rickycatalog.util.Constants.Companion.FIRESTORE_PATH_COMMENTS
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val userDataStore: DataStore<Preferences>,
    private val database: FirebaseFirestore
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

            if (response.isSuccessful) {
                response.body().let {
                    return@let BasicApiResponse.success(it)
                }
            } else {
                BasicApiResponse.error("Error code from https request was ${response.code()}", null)
            }

        } catch (e: Exception) {
            BasicApiResponse.error(
                "An unknown error occurred when trying to get Character's List",
                null
            )
        }
    }

    override suspend fun getCharactersByPage(pageIndex: Int): BasicApiResponse<CharacterListResponse> {
        return try {

            val response = apiInterface.getCharactersByPage(pageIndex)

            if (response.isSuccessful) {
                response.body().let {
                    return@let BasicApiResponse.success(it)
                }
            } else {
                BasicApiResponse.error("Error code from https request was ${response.code()}", null)
            }

        } catch (e: Exception) {
            BasicApiResponse.error(
                "An unknown error occurred when trying to get Character's List",
                null
            )
        }
    }

    override suspend fun getAllComments(): Pair<List<Comments>, Boolean> {
        val commentsList: List<Comments>

        try {
            commentsList = database.collection(FIRESTORE_PATH_COMMENTS).get()
                .await().documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(Comments::class.java)
                }

        } catch (e: Exception) {
            Log.d("An unknown error occurred", e.toString())
            return Pair(emptyList(), true)
        }

        return if (commentsList.isEmpty()) {
            Pair(commentsList, true)
        } else {
            Pair(commentsList, false)
        }
    }

    override suspend fun upSert(
        comments: Comments,
        onResult: (Boolean, String) -> Unit
    ) {
        try {

            database.collection(FIRESTORE_PATH_COMMENTS).document(comments.id.toString())
                .set(comments)
                .addOnSuccessListener {
                    onResult(true, "")
                }.addOnFailureListener {
                    onResult(false, it.localizedMessage ?: "")
                }

        } catch (e: Exception) {
            Log.e("Error when adding an comment to a character.", e.toString())
        }
    }

    override suspend fun deleteComment(
        comments: Comments,
        onResult: (Boolean, String) -> Unit
    ) {
        try {

            database.collection(FIRESTORE_PATH_COMMENTS).document(comments.id.toString())
                .delete()
                .addOnSuccessListener {
                    onResult(true, "")
                }.addOnFailureListener {
                    onResult(false, it.localizedMessage ?: "")
                }

        } catch (e: Exception) {
            Log.e("Error when deleting an comment to a character.", e.toString())
        }
    }

    companion object {
        private val DARK_MODE_THEME = stringPreferencesKey("DARK_MODE_THEME")
    }
}