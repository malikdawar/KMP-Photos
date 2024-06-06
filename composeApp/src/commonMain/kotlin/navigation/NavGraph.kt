package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject
import ui.AppViewModel
import ui.detail.PhotoDetailScreen
import ui.home.HomeScreen
import ui.home.presentation.HomeViewModel

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun Navigation(
    navigator: Navigator,
    sharedViewModel: AppViewModel,
) {
    val homeViewModel = koinInject<HomeViewModel>()
    NavHost(
        navigator = navigator,
        initialRoute = NavigationScreen.Home.route,
    ) {
        scene(route = NavigationScreen.Home.route) {
            HomeScreen(navigator, viewModel = homeViewModel, sharedViewModel = sharedViewModel)
        }

        scene(route = NavigationScreen.PhotoDetail.route) {
            PhotoDetailScreen(navigator, sharedViewModel)
        }
    }
}

@Composable
fun currentRoute(navigator: Navigator): String? {
    return navigator.currentEntry.collectAsState(null).value?.route?.route
}
