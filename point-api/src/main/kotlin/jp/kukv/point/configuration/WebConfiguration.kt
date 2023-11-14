package jp.kukv.point.configuration

import com.github.ricky12awesome.jss.encodeToSchema
import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.doublereceive.DoubleReceive
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.routing.routing
import jp.kukv.environment.EnvironmentPlugin
import jp.kukv.point.configuration.koin.InjectConfiguration
import jp.kukv.point.endpoint.handler
import jp.kukv.point.endpoint.point.pointRouting
import jp.kukv.point.endpoint.transaction.transactionRouting
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.koin

fun Application.module() {
    install(EnvironmentPlugin)

    koin {
        modules(InjectConfiguration().module)
    }

    val kotlinxJson =
        Json {
            prettyPrint = true
            isLenient = true
        }

    install(ContentNegotiation) {
        json(kotlinxJson)
    }

    install(SwaggerUI) {
        encoding {
            schemaEncoder { type ->
                kotlinxJson.encodeToSchema(serializer(type), generateDefinitions = false)
            }
            schemaDefinitionsField = "definitions"
            exampleEncoder { type, value ->
                kotlinxJson.encodeToString(serializer(type!!), value)
            }
        }

        swagger {
            swaggerUrl = "swagger-ui"
            forwardRoot = true
        }
        info {
            title = "Point API"
            version = "0.0.1"
            description = "ポイント情報を管理するAPI"
            contact {
                name = "kukv"
                url = "https://github.com/kukv"
                email = "example@example.com"
            }
        }
        server {
            url = "http://localhost:8080"
            description = "Local Server"
        }
    }

    routing {
        pointRouting()
        transactionRouting()
    }

    install(StatusPages) {
        handler()
    }

    install(DoubleReceive)
}
