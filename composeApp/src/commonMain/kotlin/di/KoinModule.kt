package di

import data.remote.ApiInterface
import data.remote.ApiInterfaceImpl
import data.repository.PhotosRepository
import data.repository.PhotosRepositoryImpl
import domain.usecases.FetchPhotosUseCase
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import ui.home.presentation.HomeViewModel

private const val CLIENT_ID = "Client-ID"
private const val AUTH = "Authorization"
private const val BASE_URL = "https://api.unsplash.com"
private const val API_KEY = "63LQG-m3idym8cVfpkChOj65l1yspeVkDuA3fKVA2YA"

fun initKoin(baseUrl: String = BASE_URL) = initKoin(enableNetworkLogs = true, baseUrl = baseUrl) {}

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
        getDataModule(
            enableNetworkLogs,
            baseUrl,
        )

fun getDataModule(
    enableNetworkLogs: Boolean,
    baseUrl: String,
) = module {
    factory { HomeViewModel(get()) }

    single {
        PhotosRepositoryImpl(get())
    }

    single {
        ApiInterfaceImpl(get())
    }

    single {
        createHttpClient(
            baseUrl,
            enableNetworkLogs = enableNetworkLogs,
        )
    }
}

fun getUseCaseModule() =
    module {
        single {
            FetchPhotosUseCase(get())
        }
    }

fun createHttpClient(
    baseUrl: String,
    enableNetworkLogs: Boolean,
) = HttpClient(CIO) {

    install(DefaultRequest) {
        url(baseUrl)
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        header(AUTH, "$CLIENT_ID $API_KEY")
    }
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                isLenient = true
            },
        )
    }
    install(HttpTimeout) {
        val timeout = 30000L
        connectTimeoutMillis = timeout
        requestTimeoutMillis = timeout
        socketTimeoutMillis = timeout
    }
    if (enableNetworkLogs) {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
}

