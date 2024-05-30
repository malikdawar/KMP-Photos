package ui.home.presentation

sealed class HomeScreenSideEvent

data object GetPhotos : HomeScreenSideEvent()
data object LoadMorePhotos : HomeScreenSideEvent()
