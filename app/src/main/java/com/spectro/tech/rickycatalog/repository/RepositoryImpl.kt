package com.spectro.tech.rickycatalog.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
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

    companion object {
        private val DARK_MODE_THEME = stringPreferencesKey("DARK_MODE_THEME")
    }
}