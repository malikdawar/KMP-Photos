package ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import domain.model.PhotoModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ui.home.presentation.HomeViewModel
import ui.home.presentation.LoadMorePhotos
import utils.cornerRadius

@Composable
internal fun ImageList(
    photos: List<PhotoModel>,
    viewModel: HomeViewModel,
    onclick: (photoModel: PhotoModel) -> Unit,
) {
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 10.dp),
        content = {
            items(items = photos) {
                Column(
                    modifier =
                        Modifier.padding(
                            start = 5.dp,
                            end = 5.dp,
                            top = 0.dp,
                            bottom = 10.dp,
                        ),
                ) {
                    CoilImage(
                        imageModel = { it.imageURL },
                        imageOptions =
                            ImageOptions(
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.Center,
                                contentDescription = "Image item",
                                colorFilter = null,
                            ),
                        component =
                            rememberImageComponent {
                                +CircularRevealPlugin(
                                    duration = 800,
                                )
                            },
                        modifier =
                            Modifier.height(250.dp).fillMaxWidth().cornerRadius(10)
                                .shimmerBackground(
                                    RoundedCornerShape(5.dp),
                                ).clickable {
                                    onclick(it)
                                },
                    )
                }
            }
        },
    )

    // Adding scroll listener
    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo }
            .collectLatest { visibleItems ->
                if (visibleItems.isNotEmpty()) {
                    val lastVisibleItem = visibleItems.last()
                    val totalItems = gridState.layoutInfo.totalItemsCount

                    if (lastVisibleItem.index == totalItems - 1) {
                        coroutineScope.launch {
                            viewModel.onIntent(LoadMorePhotos)
                        }
                    }
                }
            }
    }
}
