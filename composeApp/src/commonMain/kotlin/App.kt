import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import di.initKoin
import kotlinx.coroutines.ExperimentalCoroutinesApi
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.BackHandler
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import navigation.Navigation
import navigation.NavigationScreen
import navigation.currentRoute
import ui.AppViewModel
import ui.component.AppBarWithArrow
import utils.AppString

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
internal fun App() {
    initKoin()
    PreComposeApp {
        val navigator = rememberNavigator()
        val isAppBarVisible = remember { mutableStateOf(true) }

        BackHandler(isAppBarVisible.value.not()) {
            isAppBarVisible.value = true
        }

        MaterialTheme {
            Scaffold(topBar = {
                AppBarWithArrow(
                    AppString.APP_TITLE,
                    isBackEnable = isBackButtonEnable(navigator),
                ) {
                    navigator.popBackStack()
                }
            }) {
                Navigation(navigator, AppViewModel())
            }
        }
    }
}

@Composable
fun isBackButtonEnable(navigator: Navigator): Boolean {
    return when (currentRoute(navigator)) {
        NavigationScreen.Home.route -> {
            false
        }

        else -> {
            true
        }
    }
}
