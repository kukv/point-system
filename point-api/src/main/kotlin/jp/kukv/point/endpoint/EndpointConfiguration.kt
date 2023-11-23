package jp.kukv.point.endpoint

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.routing.routing
import jp.kukv.point.endpoint.reservation.reflection
import jp.kukv.point.endpoint.reservation.reservation
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("ExceptionAdvice")

fun Application.handler() {
    install(StatusPages) {
        handler()
    }

    routing {
        reservation()
        reflection()
    }
}
