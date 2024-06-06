package ui.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.DataState
import domain.model.PhotoModel
import domain.usecases.FetchPhotosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(private val fetchPhotosUseCase: FetchPhotosUseCase) : ViewModel() {
    private var pageNumber = 1
    private val _homeUiStateFlow = MutableStateFlow<HomeUiState>(LoadingState)
    val homeUiState: StateFlow<HomeUiState> get() = _homeUiStateFlow

    private var _photosListFlow = MutableStateFlow<List<PhotoModel>>(emptyList())
    val photosList: StateFlow<List<PhotoModel>> get() = _photosListFlow

    fun onIntent(intent: HomeScreenSideEvent) {
        when (intent) {
            is GetPhotos -> {
                fetchPhotos(page = pageNumber)
            }

            is LoadMorePhotos -> {
                loadMorePhotos()
            }
        }
    }

    private fun loadMorePhotos() {
        pageNumber++
        fetchPhotos(pageNumber)
    }

    private fun fetchPhotos(page: Int) {
        viewModelScope.launch {
            _homeUiStateFlow.value = LoadingState
            fetchPhotosUseCase.invoke(pageNumber = page).collectLatest { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        if (page == 1) {
                            // First page
                            _homeUiStateFlow.value = ContentState
                            _photosListFlow.value = dataState.data
                        } else {
                            // Any other page
                            _homeUiStateFlow.value = ContentNextPageState

                            val currentList = arrayListOf<PhotoModel>()
                            _photosListFlow.value.let { currentList.addAll(it) }
                            dataState.data.let {
                                currentList.addAll(it)
                            }
                            _photosListFlow.value = currentList
                        }
                    }

                    is DataState.Error -> {
                        _homeUiStateFlow.value = ErrorState(dataState.message)
                    }
                }
            }
        }
    }
}
