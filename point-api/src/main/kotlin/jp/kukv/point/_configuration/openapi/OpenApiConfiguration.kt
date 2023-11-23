package jp.kukv.point._configuration.openapi

import com.github.ricky12awesome.jss.encodeToSchema
import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.server.application.Application
import io.ktor.server.application.install
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import org.koin.ktor.ext.inject

fun Application.openApi() {
    val json by inject<Json>()
    install(SwaggerUI) {
        encoding {
            schemaEncoder { type ->
                json.encodeToSchema(serializer(type), generateDefinitions = false)
            }
            schemaDefinitionsField = "definitions"
            exampleEncoder { type, value ->
                json.encodeToString(serializer(type!!), value)
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
}
