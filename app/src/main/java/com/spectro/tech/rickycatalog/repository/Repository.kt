package com.spectro.tech.rickycatalog.repository

interface Repository {

    suspend fun setDarkMode(darkMode: Boolean)

    suspend fun showDarkMode(): Boolean

}