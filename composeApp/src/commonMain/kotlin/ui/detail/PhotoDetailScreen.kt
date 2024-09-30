package ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil3.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import domain.model.PhotoModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import moe.tlaster.precompose.navigation.Navigator
import theme.DefaultBackgroundColor
import ui.AppViewModel
import utils.AppString.FOLLOWERS
import utils.AppString.INSTAGRAM
import utils.AppString.PHOTOS

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun PhotoDetailScreen(
    navigator: Navigator,
    sharedViewModel: AppViewModel,
) {
    val selectedPhoto by sharedViewModel.selectedPhoto.collectAsState()

    println(selectedPhoto)

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(
                    DefaultBackgroundColor,
                ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        selectedPhoto?.let {
            UiDetail(it)
        }
    }
}

@Composable
fun UiDetail(data: PhotoModel) {
    Column(
        modifier =
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(4.dp),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            // CoilImage
            CoilImage(
                imageModel = {
                    data.imageURL
                },
                imageOptions =
                    ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                        contentDescription = "Photo",
                        colorFilter = null,
                    ),
                component =
                    rememberImageComponent {
                        +CircularRevealPlugin(
                            duration = 800,
                        )
                    },
                modifier = Modifier.fillMaxSize(),
            )

            // Breadcrumb-like indicator for likes
            Box(
                modifier =
                    Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomStart),
            ) {
                Box(
                    modifier =
                        Modifier
                            .background(
                                color = Color.Black.copy(alpha = 0.5f),
                                shape = CircleShape,
                            )
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Likes Icon",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${data.photoLikes} likes",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }

        // Photographer information
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
        ) {
            Text(
                text = data.alt.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(), // Ensure text takes full width
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Photographer details

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            data.photographerAvatar?.let { avatarUrl ->
                CoilImage(
                    imageModel = {
                        avatarUrl
                    },
                    modifier =
                        Modifier
                            .size(100.dp)
                            .clip(shape = CircleShape),
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            Spacer(modifier = Modifier.height(4.dp))

            Column {
                Text(
                    text = data.photographer,
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "$INSTAGRAM${data.photographerInstagram}",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "$FOLLOWERS ${data.photographerFollowers}",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "$PHOTOS${data.photographerPhotos}",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        // Description
        Spacer(modifier = Modifier.height(16.dp))
        data.description?.let { description ->
            Text(
                text = description.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp,
                maxLines = 5,
            )
        }

        // Alt text
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
        ) {
            Text(
                text = data.alt.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                color = Color.Gray,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}
