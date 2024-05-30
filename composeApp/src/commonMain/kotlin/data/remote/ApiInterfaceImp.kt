package data.remote

import data.model.Photo
import data.remote.EndPoints.SERVICE_FETCH_API
import data.remote.HttpClientProvider.client
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.encodedPath

class ApiInterfaceImp : ApiInterface {
    override suspend fun loadPhotos(
        pageNumber: Int,
        pageSize: Int,
        orderBy: String,
    ): List<Photo> {
        return client.get {
            url {
                encodedPath = SERVICE_FETCH_API
                parameters.append("page", pageNumber.toString())
                parameters.append("per_page", pageSize.toString())
                parameters.append("order_by", orderBy)
            }
        }.body<List<Photo>>()
    }
}
