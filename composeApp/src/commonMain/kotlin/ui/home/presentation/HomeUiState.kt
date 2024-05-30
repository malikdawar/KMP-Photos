package ui.home.presentation

sealed class HomeUiState

data object LoadingState : HomeUiState()

data object ContentState : HomeUiState()

data object ContentNextPageState : HomeUiState()

class ErrorState(val message: String) : HomeUiState()
