package ui

import androidx.lifecycle.ViewModel
import domain.model.PhotoModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@ExperimentalCoroutinesApi
class AppViewModel : ViewModel() {
    private val _selectedPhoto = MutableStateFlow<PhotoModel?>(null)
    val selectedPhoto: StateFlow<PhotoModel?> = _selectedPhoto

    fun saveSelectedPhoto(photoModel: PhotoModel) {
        _selectedPhoto.value = photoModel
    }
}
