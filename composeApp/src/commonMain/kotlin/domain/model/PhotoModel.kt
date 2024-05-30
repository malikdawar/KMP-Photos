package domain.model

import data.model.Photo
import kotlinx.serialization.Serializable

@Serializable
data class PhotoModel(
    val id: String,
    val photographer: String,
    val photographerAvatar: String?,
    val photographerInstagram: String,
    val photographerFollowers: Int,
    val photographerPhotos: Int,
    val imageURL: String,
    val description: String?,
    val alt: String,
    val photoLikes: Int,
)

fun List<Photo>.toPhotoModel(): List<PhotoModel> {
    return this.map {
        PhotoModel(
            id = it.id,
            photographer = it.photographer.name,
            photographerAvatar = it.photographer.profileImage.large,
            photographerInstagram = it.photographer.instagramUsername ?: "Unsplash",
            photographerFollowers = it.photographer.totalLikes,
            photographerPhotos = it.photographer.totalPhotos,
            imageURL = it.photoSrc.full,
            description = it.description,
            alt = it.alt,
            photoLikes = it.likes,
        )
    }
}
