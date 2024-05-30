package data.remote

import data.model.Photo

interface ApiInterface {
    suspend fun loadPhotos(
        pageNumber: Int,
        pageSize: Int,
        orderBy: String,
    ): List<Photo>
}
