package com.spectro.tech.rickycatalog.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.firebase.firestore.FirebaseFirestore
import com.spectro.tech.rickycatalog.repository.CharacterListDataSource
import com.spectro.tech.rickycatalog.repository.Repository
import com.spectro.tech.rickycatalog.repository.RepositoryImpl
import com.spectro.tech.rickycatalog.service.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRepository(
        apiInterface: ApiInterface,
        userDataStore: DataStore<Preferences>,
        database: FirebaseFirestore
    ): Repository {
        return RepositoryImpl(apiInterface, userDataStore, database)
    }

    @Singleton
    @Provides
    fun provideCharacterListDataSource(
        repository: Repository
    ) : CharacterListDataSource {
        return CharacterListDataSource(repository)
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(
        @ApplicationContext appContext: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = {
                appContext.preferencesDataStoreFile(USER_PREFERENCES)
            }
        )
    }

    @Singleton
    @Provides
    fun provideFireStoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}