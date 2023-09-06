package com.spectro.tech.rickycatalog.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spectro.tech.rickycatalog.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private var _toolbarIsVisible = MutableLiveData<Boolean>()
    val toolbarIsvisible: LiveData<Boolean> = _toolbarIsVisible

    private var _toolbarTitleText = MutableLiveData<String>()
    val toolbarTitleText: LiveData<String> = _toolbarTitleText

    private var _toolbarBackButtonIsVisible = MutableLiveData<Boolean>()
    val toolbarBackButtonIsVisible: LiveData<Boolean> = _toolbarBackButtonIsVisible

    private var _statusBarIsVisible = MutableLiveData<Boolean>()
    val statusBarIsVisible: LiveData<Boolean> = _statusBarIsVisible

    private var _darkMode = MutableLiveData<Boolean>()
    val darkMode: LiveData<Boolean> = _darkMode

    fun setToolbarVisibility(show: Boolean) {
        _toolbarIsVisible.postValue(show)
    }

    fun setToolbarTitleText(toolbarTitle: String) {
        _toolbarTitleText.postValue(toolbarTitle)
    }

    fun setToolbarBackButtonVisibility(show: Boolean) {
        _toolbarBackButtonIsVisible.postValue(show)
    }

    fun showStatusBar(show: Boolean) {
        _statusBarIsVisible.postValue(show)
    }

    fun setDarkMode(darkMode: Boolean) {
        viewModelScope.launch {
            repository.setDarkMode(darkMode)
        }
    }

    fun showDarkMode() {
        viewModelScope.launch {
            val response = repository.showDarkMode()
            _darkMode.postValue(response)
        }
    }
}