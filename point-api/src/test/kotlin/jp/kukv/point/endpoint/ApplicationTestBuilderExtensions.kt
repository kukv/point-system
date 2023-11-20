package jp.kukv.point.endpoint

import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.ApplicationTestBuilder
import kotlinx.serialization.json.Json
import io.ktor.client.request.get as ktorClientRequestGet
import io.ktor.client.request.post as ktorClientRequestPost

private fun ApplicationTestBuilder.createTestClient() =
    createClient {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                },
            )
        }
    }

suspend fun ApplicationTestBuilder.get(
    url: String,
    block: HttpRequestBuilder.() -> Unit,
): HttpResponse {
    val httpClient = createTestClient()

    return httpClient.use {
        it.ktorClientRequestGet(url) {
            block()
        }
    }
}

suspend fun ApplicationTestBuilder.post(
    url: String,
    block: HttpRequestBuilder.() -> Unit,
): HttpResponse {
    val httpClient = createTestClient()

    return httpClient.use {
        it.ktorClientRequestPost(url) {
            contentType(ContentType.Application.Json)
            block()
        }
    }
}
