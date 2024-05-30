package navigation

sealed class NavigationScreen(
    val route: String,
) {
    data object Home : NavigationScreen(route = "home_screen")

    data object PhotoDetail : NavigationScreen(route = "photo_detail_screen")
}
