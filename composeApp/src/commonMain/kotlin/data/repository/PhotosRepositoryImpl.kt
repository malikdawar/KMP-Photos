package data.repository

import data.DataState
import data.model.Photo
import data.remote.ApiInterface
import data.remote.ApiInterfaceImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * This is an implementation of [PhotosRepository] to handle communication with [ApiInterface] server.
 * @author Malik Dawar
 */
class PhotosRepositoryImpl : PhotosRepository {
    // TODO add koin
    private val apiService: ApiInterface = ApiInterfaceImp()

    override suspend fun loadPhotos(
        pageNumber: Int,
        pageSize: Int,
        orderBy: String,
    ): Flow<DataState<List<Photo>>> {
        return flow {
            try {
                val result =
                    apiService.loadPhotos(
                        pageNumber = pageNumber,
                        pageSize = pageSize,
                        orderBy = orderBy,
                    )
                emit(DataState.Success(result))
            } catch (e: Exception) {
                emit(DataState.Error(e.message.toString()))
            }
        }
    }
}
