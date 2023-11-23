package jp.kukv.point._configuration

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import jp.kukv.environment.EnvironmentPlugin
import jp.kukv.point._configuration.exposed.PointConfiguration
import kotlinx.serialization.json.Json
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.ksp.generated.module
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.koin

@Module(includes = [ PointConfiguration::class ])
@ComponentScan("jp.kukv.point")
class WebConfiguration {
    @Single
    fun json(): Json =
        Json {
            prettyPrint = true
            isLenient = true
        }
}

fun Application.configuration() {
    install(EnvironmentPlugin)

    koin {
        modules(WebConfiguration().module)
    }

    val json by inject<Json>()
    install(ContentNegotiation) {
        json(json)
    }
}
