package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
// response, for the search API
@Serializable
data class PhotosResponse(
    @SerialName("total") val totalResults: Int,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("results") val photos: List<Photo>,
)*/

@Serializable
data class Photo(
    @SerialName("id") val id: String,
    @SerialName("asset_type") val assetType: String,
    @SerialName("urls") val photoSrc: PhotoSrc,
    @SerialName("user") val photographer: Photographer,
    @SerialName("description") val description: String?,
    @SerialName("alt_description") val alt: String,
    @SerialName("likes") val likes: Int,
)

@Serializable
data class PhotoSrc(
    @SerialName("raw") val original: String,
    @SerialName("regular") val medium: String,
    @SerialName("full") val full: String,
    @SerialName("small") val small: String,
)

@Serializable
data class Photographer(
    @SerialName("name") val name: String,
    @SerialName("instagram_username") val instagramUsername: String?,
    @SerialName("profile_image") val profileImage: ProfilePhotoSrc,
    @SerialName("total_photos") val totalPhotos: Int,
    @SerialName("total_likes") val totalLikes: Int,
)

@Serializable
data class ProfilePhotoSrc(
    @SerialName("small") val portrait: String?,
    @SerialName("large") val large: String?,
)
