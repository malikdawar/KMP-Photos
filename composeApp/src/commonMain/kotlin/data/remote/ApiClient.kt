package data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientProvider {
    val client: HttpClient by lazy {
        HttpClient {
            defaultRequest {
                url {
                    takeFrom(BASE_URL)
                    header(AUTH, "$CLIENT_ID $API_KEY")
                }
            }
            expectSuccess = true
            install(HttpTimeout) {
                val timeout = 30000L
                connectTimeoutMillis = timeout
                requestTimeoutMillis = timeout
                socketTimeoutMillis = timeout
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    },
                )
            }
            install(HttpRedirect) {
                this@HttpClient.followRedirects = true
            }
        }
    }

    private const val CLIENT_ID = "Client-ID"
    private const val AUTH = "Authorization"
    private const val BASE_URL = "https://api.unsplash.com"
    private const val API_KEY = "63LQG-m3idym8cVfpkChOj65l1yspeVkDuA3fKVA2YA"
}
