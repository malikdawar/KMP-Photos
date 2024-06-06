package domain.usecases

import data.DataState
import data.repository.PhotosRepositoryImpl
import domain.model.PhotoModel
import domain.model.toPhotoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import utils.AppConstant.ORDER_BY
import utils.AppConstant.PAGE_OFFSET

/**
 * A use-case to load the photos from API.
 * @author Malik Dawar
 */
class FetchPhotosUseCase(private val repository: PhotosRepositoryImpl) {
    suspend operator fun invoke(
        pageNumber: Int = 1,
        pageSize: Int = PAGE_OFFSET,
        orderBy: String = ORDER_BY,
    ): Flow<DataState<List<PhotoModel>>> {
        return repository.loadPhotos(pageNumber, pageSize, orderBy)
            .map { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        val photoModels = dataState.data.toPhotoModel()
                        DataState.Success(photoModels)
                    }

                    is DataState.Error -> DataState.Error(dataState.message)
                }
            }
    }
}
