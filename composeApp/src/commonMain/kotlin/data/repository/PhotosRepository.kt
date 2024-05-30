package data.repository

import data.DataState
import data.model.Photo
import kotlinx.coroutines.flow.Flow

/**
 * ImagineRepository is an interface data layer to handle communication with any data source such as Server or local database.
 * @see [PhotosRepositoryImpl] for implementation of this class to utilize Unsplash API.
 * @author Malik Dawar
 */
interface PhotosRepository {
    suspend fun loadPhotos(
        pageNumber: Int,
        pageSize: Int,
        orderBy: String,
    ): Flow<DataState<List<Photo>>>
}
