package ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import domain.model.PhotoModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import moe.tlaster.precompose.navigation.Navigator
import navigation.NavigationScreen
import ui.AppViewModel
import ui.component.ImageList
import ui.component.ProgressIndicator
import ui.home.presentation.ContentNextPageState
import ui.home.presentation.ContentState
import ui.home.presentation.ErrorState
import ui.home.presentation.GetPhotos
import ui.home.presentation.HomeViewModel
import ui.home.presentation.LoadingState

@OptIn(ExperimentalCoroutinesApi::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun HomeScreen(
    navigator: Navigator,
    viewModel: HomeViewModel,
    sharedViewModel: AppViewModel,
) {
    val isLoading = remember { mutableStateOf(false) }
    val photos = remember { mutableStateListOf<PhotoModel>() }

    LaunchedEffect(Unit) {
        viewModel.onIntent(GetPhotos)
    }

    LaunchedEffect(viewModel.photosList) {
        viewModel.photosList.collectLatest {
            if (it.isNotEmpty()) {
                photos.clear()
                photos.addAll(it)
            }
        }
    }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        ImageList(photos = photos, viewModel = viewModel) {
            sharedViewModel.saveSelectedPhoto(it)
            navigator.navigate(NavigationScreen.PhotoDetail.route)
        }
        if (isLoading.value) {
            ProgressIndicator()
        }
    }

    viewModel.homeUiState.collectAsState().value.let {
        when (it) {
            is LoadingState -> {
                isLoading.value = true
            }

            is ContentState, is ContentNextPageState -> {
                isLoading.value = false
            }

            is ErrorState -> {
                isLoading.value = false
            }
        }
    }
}
