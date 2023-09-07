package com.spectro.tech.rickycatalog.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.spectro.tech.rickycatalog.model.domain.CharacterDTO
import com.spectro.tech.rickycatalog.model.domain.Comments
import com.spectro.tech.rickycatalog.model.domain.EpisodeDTO
import com.spectro.tech.rickycatalog.repository.CharacterListDataSource
import com.spectro.tech.rickycatalog.repository.Repository
import com.spectro.tech.rickycatalog.util.BasicApiResponse
import com.spectro.tech.rickycatalog.util.Constants
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

    private var _character = MutableLiveData<CharacterDTO>()
    val character: LiveData<CharacterDTO> = _character

    private var _darkMode = MutableLiveData<Boolean>()
    val darkMode: LiveData<Boolean> = _darkMode

    private var _episodeList = MutableLiveData<BasicApiResponse<List<EpisodeDTO>>>()
    val episodeList: LiveData<BasicApiResponse<List<EpisodeDTO>>> = _episodeList

    private var _firestoreComments = MutableLiveData<List<Comments>>()
    val firestoreComments: LiveData<List<Comments>> = _firestoreComments

    val flow = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PRE_FETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        CharacterListDataSource(repository)
    }.flow.cachedIn(viewModelScope)

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

    fun setCharacter(characterDTO: CharacterDTO) {
        _character.postValue(characterDTO)
    }

    fun getEpisodeList(episodesId: String) {
        viewModelScope.launch {
            val response = repository.getEpisodeRange(episodesId)
            _episodeList.postValue(response)
        }
    }

    fun getComments(){
        viewModelScope.launch {
            val response = repository.getAllComments()
            _firestoreComments.postValue(response.first!!)

        }
    }

    fun upsertComment(comments: Comments, onResult: (Boolean, String) -> Unit){
        viewModelScope.launch {
            repository.upSert(comments, onResult)
        }
    }

    fun deleteComment(comments: Comments, onResult: (Boolean, String) -> Unit){
        viewModelScope.launch {
            repository.deleteComment(comments, onResult)
        }
    }

    fun clearCommentsList(){
        _firestoreComments.value = emptyList()
    }
}