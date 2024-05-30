package di

import data.remote.ApiInterface
import data.remote.ApiInterfaceImp
import data.repository.PhotosRepository
import data.repository.PhotosRepositoryImpl
import domain.usecases.FetchPhotosUseCase
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import ui.home.presentation.HomeViewModel

fun initKoin(baseUrl: String = "URL") = initKoin(enableNetworkLogs = true, baseUrl = baseUrl) {}

fun initKoin(
    enableNetworkLogs: Boolean = false,
    baseUrl: String,
    appDeclaration: KoinAppDeclaration = {},
) = startKoin {
    appDeclaration()
    modules(commonModule(enableNetworkLogs = enableNetworkLogs, baseUrl))
}

fun commonModule(
    enableNetworkLogs: Boolean,
    baseUrl: String,
) = getUseCaseModule() +
    getDateModule(
        enableNetworkLogs,
        baseUrl,
    )

fun getDateModule(
    enableNetworkLogs: Boolean,
    baseUrl: String,
) = module {
    factory { HomeViewModel() }

    single<PhotosRepository> {
        PhotosRepositoryImpl()
    }

    single<ApiInterface> {
        ApiInterfaceImp()
    }

    single { createJson() }

    single {
        createHttpClient(
            get(),
            get(),
            enableNetworkLogs = enableNetworkLogs,
        )
    }
}

fun getUseCaseModule() =
    module {
        single {
            FetchPhotosUseCase()
        }
    }

fun createHttpClient(
    httpClientEngine: HttpClientEngine,
    json: Json,
    enableNetworkLogs: Boolean,
) = HttpClient(httpClientEngine) {
    install(ContentNegotiation) {
        json(json)
    }
    if (enableNetworkLogs) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
}

fun createJson() =
    Json {
        isLenient = true
        ignoreUnknownKeys = true
    }
